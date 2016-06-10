package org.vsg.cusp.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class StandardServerMonitor {

	private boolean await;

	public boolean isAwait() {
		return await;
	}

	public void setAwait(boolean await) {
		this.await = await;
	}

	protected ClassLoader parentClassLoader = StandardServerMonitor.class.getClassLoader();
    
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
        	URL url = new URL(this.configFile);
        	JSONObject jObj = (JSONObject)JSON.parse( IOUtils.toString(url.toURI() , "utf-8") );
        	
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
        		
        		this.engines.add( inst );


        	}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
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
    private List<ServEngine> engines = new ArrayList<ServEngine>();


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

        boolean isConfig = true;

        if (args.length < 1) {

            return false;
        }

        for (int i = 0; i < args.length; i++) {
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
        }

        return true;
    }
    
	/**
	 * start a new server monitor instance 
	 */
	public void start() {
		
		long t1 = System.nanoTime();
		
		this.setAwait(true);
		
		Iterator<ServEngine> engineIter =  engines.iterator();
		while (engineIter.hasNext()){
			ServEngine engineItem =  engineIter.next();
			engineItem.start();
		}
	
		
		if (this.isAwait()) {
			await();
			stop();
		}
	}
	
	public void await() {
		// --- scan self await ---
	}
	
	public void stop() {
		
	}
	
	
	
}
