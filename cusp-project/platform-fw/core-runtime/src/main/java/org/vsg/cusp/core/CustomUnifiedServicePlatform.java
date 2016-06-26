package org.vsg.cusp.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.modules.AbstractContainerModule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

public class CustomUnifiedServicePlatform implements Lifecycle {
	
	private static Logger log = LoggerFactory.getLogger( CustomUnifiedServicePlatform.class );

	private boolean await;

	public boolean isAwait() {
		return await;
	}

	public void setAwait(boolean await) {
		this.await = await;
	}

	protected ClassLoader parentClassLoader = CustomUnifiedServicePlatform.class.getClassLoader();
    
	public void setParentClassLoader(ClassLoader parentClassLoader) {
        this.parentClassLoader = parentClassLoader;
    }

    public ClassLoader getParentClassLoader() {
        if (parentClassLoader != null) {
            return (parentClassLoader);
        }
        return ClassLoader.getSystemClassLoader();
    }


    public void load() {

        long t1 = System.nanoTime();
        
        try {
        	File file = new File(this.configFile);
        	JSONObject jObj = (JSONObject)JSON.parse( IOUtils.toString(file.toURI() , "utf-8") );
        	
        	JSONArray engines = jObj.getJSONArray("engines");
        	for (Iterator<JSONObject> engineIter = (Iterator<JSONObject>)(Iterator)engines.iterator(); engineIter.hasNext();) {
        		JSONObject jsonObj = engineIter.next();
        		
        		
        		Map<String,String> argsMap = new LinkedHashMap<String,String>();
        		
        		String className = jsonObj.getString("class-name");
        		JSONObject arguments = jsonObj.getJSONObject("args");
        		Set<Map.Entry<String,Object>> entries =  arguments.entrySet();
        		for (Map.Entry<String, Object> entry : entries) {
        			argsMap.put(entry.getKey(), entry.getValue().toString());
        		}

        		
        		Class<ServEngine> servEngineCls = (Class<ServEngine>)Class.forName(className);

        		ServEngine inst =  servEngineCls.newInstance();
        		inst.init(argsMap);
        		
        		// ---  add engine module ---
        		if (inst instanceof Module) {
        			modules.add( (Module) inst );
        		}

        	}
        	
        	// --- custom server ---
        	JSONObject server = jObj.getJSONObject("server");
        	shutdown = server.getString("cmd-shutdown");
        	port = server.getIntValue("port");        	
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    /**
     * define all engines
     */
    private List<Module> modules = new ArrayList<Module>();


    /*
     * Load using arguments
     */
    public void load(String args[]) {
        
    	try {
            if (arguments(args)) {
                load();
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }
    
    private String configFile;

    
    protected boolean arguments(String args[]) {

        //boolean isConfig = true;

        if (args.length < 1) {
            return false;
        }


        for (int i = 0; i < args.length; i++) {
        	// --- parse profile ---
        	if (args[i].startsWith("-conf_file=")) {
        		configFile = args[i].replace("-conf_file=", "");
        	}
        	
        	/*
        	if (isConfig) {
                configFile = args[i];
                isConfig = false;
            } else if (args[i].equals("-config")) {
                isConfig = true;
            } else if (args[i].equals("-nonaming")) {
                //setUseNaming(false);
            } else if (args[i].equals("-help")) {

                return false;
            } else if (args[i].equals("start")) {
                // NOOP
            } else if (args[i].equals("configtest")) {
                // NOOP
            } else if (args[i].equals("stop")) {
                // NOOP
            } else {

                return false;
            }
        	*/
        }


        return true;
    }
    
    /**
     * The current state of the source component.
     */
    private volatile LifecycleState state = LifecycleState.NEW;    
    
    /**
     * {@inheritDoc}
     */
    public LifecycleState getState() {
        return state;
    }    
    
    public void setState(LifecycleState state) {
    	this.state = state;
    }
    
    private Lock lock = new ReentrantLock(true);
    
    
	/**
	 * start a new server monitor instance 
	 */
	public void start() {
		
		long t1 = System.nanoTime();
		
		
        setState(LifecycleState.STARTING);
        
        try {
        	
			lock.lock();
			
			// --- start and boot container ---
			ContainerBase cb = new ContainerBase();
			// --- set parent class loader ---
			File cuspHome = new File(System.getProperty("cusp.home"));
			
			cb.setCuspHome(cuspHome);
			cb.setParentClassLoader( parentClassLoader );
			cb.init();
			
			// --- init another instance ---
			for (Module module : this.modules) {
				if (module instanceof AbstractContainerModule) {
					((AbstractContainerModule)module).setRunningContainer( cb );
				}
			}
			
			// --- create inject ---
			Stage stage = Stage.PRODUCTION;
			Injector injector = Guice.createInjector( stage , this.modules);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		

		if (this.isAwait()) {
			await();
			stop();
		}
		
		// --- exist program ---
		Runtime.getRuntime().exit(0);
	}
	
	
    private volatile ServerSocket awaitSocket = null;
    
    private volatile Thread awaitThread = null;  

    private volatile boolean stopAwait = false; 
    

    /**
     * The shutdown command string we are looking for.
     */
    private String shutdown = "SHUTDOWN"; 
    
    /**
     * A random number generator that is <strong>only</strong> used if
     * the shutdown command string is longer than 1024 characters.
     */
    private Random random = null;    
    
	public void await() {
		// --- scan self await ---

        if( port == -2 ) {
            // undocumented yet - for embedding apps that are around, alive.
            return;
        }
        if( port==-1 ) {
            try {
                awaitThread = Thread.currentThread();
                while(!stopAwait) {
                    try {
                        Thread.sleep( 10000 );
                    } catch( InterruptedException ex ) {
                        // continue and check the flag
                    }
                }
            } finally {
                awaitThread = null;
            }
            return;
        }		
		
        // Set up a server socket to wait on
        try {
        	log.info("Standard Server Listen port : " + port);
            awaitSocket = new ServerSocket(port, 1,  InetAddress.getByName(address));
        } catch (IOException e) {
            log.error("StandardServer.await: create[" + address+ ":" + port + "]: ", e);
            return;
        }
        
        try {
            awaitThread = Thread.currentThread();

            // Loop waiting for a connection and a valid command
            while (!stopAwait) {
                ServerSocket serverSocket = awaitSocket;
                if (serverSocket == null) {
                    break;
                }

                // Wait for the next connection
                Socket socket = null;
                StringBuilder command = new StringBuilder();
                try {
                    InputStream stream;
                    long acceptStartTime = System.currentTimeMillis();
                    try {
                        socket = serverSocket.accept();
                        socket.setSoTimeout(10 * 1000);  // Ten seconds
                        stream = socket.getInputStream();
                    } catch (SocketTimeoutException ste) {
                        // This should never happen but bug 56684 suggests that
                        // it does.
                       // log.warn(sm.getString("standardServer.accept.timeout", Long.valueOf(System.currentTimeMillis() - acceptStartTime)), ste);
                        continue;
                    } catch (AccessControlException ace) {
                        log.warn("StandardServer.accept security exception: "
                                + ace.getMessage(), ace);
                        continue;
                    } catch (IOException e) {
                        if (stopAwait) {
                            // Wait was aborted with socket.close()
                            break;
                        }
                        log.error("StandardServer.await: accept: ", e);
                        break;
                    }

                    // Read a set of characters from the socket
                    int expected = 1024; // Cut off to avoid DoS attack
                    while (expected < shutdown.length()) {
                        if (random == null)
                            random = new Random();
                        expected += (random.nextInt() % 1024);
                    }
                    while (expected > 0) {
                        int ch = -1;
                        try {
                            ch = stream.read();
                        } catch (IOException e) {
                            log.warn("StandardServer.await: read: ", e);
                            ch = -1;
                        }
                        if (ch < 32)  // Control character or EOF terminates loop
                            break;
                        command.append((char) ch);
                        expected--;
                    }
                } finally {
                    // Close the socket now that we are done with it
                    try {
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        // Ignore
                    }
                }

                // Match against our command string
                boolean match = command.toString().equals(shutdown);
                if (match) {
                    //log.info(sm.getString("standardServer.shutdownViaPort"));
                    break;
                } else
                    log.warn("StandardServer.await: Invalid command '"
                            + command.toString() + "' received");
            }
        } finally {
            ServerSocket serverSocket = awaitSocket;
            awaitThread = null;
            awaitSocket = null;

            // Close the server socket and return
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }        
        
	}
	
	@Override
	public void stop() {

        setState(LifecycleState.STOPPING);		
        
        try {
        	
			lock.lock();
			
			Iterator<Module> moduleIter =  modules.iterator();
			while (moduleIter.hasNext()){
				Module modItem =  moduleIter.next();
				if (modItem instanceof ServEngine) {
					((ServEngine)modItem).stop();
				}
				
			}
			
			stopAwait();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
 	}
	
	private int port = 7101;
	
	private String address = "localhost";
	
	
	
	public int getPort() {
		return port;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getShutdown() {
		return this.shutdown;
	}

    public void load_forStopServer() {

        long t1 = System.nanoTime();
        
        try {
        	File file = new File(this.configFile);
        	JSONObject jObj = (JSONObject)JSON.parse( IOUtils.toString(file.toURI() , "utf-8") );
        	
        	JSONObject server = jObj.getJSONObject("server");
        	shutdown = server.getString("cmd-shutdown");
        	port = server.getIntValue("port");
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
    }
    	
	
	/**
	 * call monitor handle event 
	 * @param arguments
	 */
    public void stopServer(String[] arguments) {

        if (arguments != null) {
            arguments(arguments);
        }
        // --- parser log ---
        load_forStopServer();

        // Stop the existing server
        if (getPort()>0) {
        	log.info("send client : " + port);
            try (Socket socket = new Socket(getAddress(), getPort());
                    OutputStream stream = socket.getOutputStream()) {
                String shutdown = getShutdown();
                for (int i = 0; i < shutdown.length(); i++) {
                    stream.write(shutdown.charAt(i));
                }
                stream.flush();
            } catch (ConnectException ce) {
                log.error("Custom Unified Service Platform Stop: ", ce);
                System.exit(1);
            } catch (IOException e) {
                log.error("Custom Unified Service Platform Stop: ", e);
                System.exit(1);
            }
        } else {
            System.exit(1);
        }
    }	

    public void stopAwait() {

        stopAwait=true;
        Thread t = awaitThread;
        if (t != null) {
            ServerSocket s = awaitSocket;
            if (s != null) {
                awaitSocket = null;
                try {
                    s.close();
                } catch (IOException e) {
                    // Ignored
                }
            }
            t.interrupt();
            try {
                t.join(1000);
            } catch (InterruptedException e) {
                // Ignored
            }
        }
    }	
	
	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LifecycleListener[] findLifecycleListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
