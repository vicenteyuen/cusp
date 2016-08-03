/**
 * 
 */
package org.vsg.cusp.engine.rapidoid;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;

import org.apache.commons.io.IOUtils;
import org.rapidoid.http.FastHttp;
import org.rapidoid.http.Req;
import org.rapidoid.http.ReqHandler;
import org.rapidoid.net.Server;
import org.rapidoid.setup.On;
import org.rapidoid.setup.OnRoute;
import org.rapidoid.setup.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.EngineCompLoaderService;
import org.vsg.cusp.core.MethodParametersMetaInfo;
import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.core.utils.ClassFilter;
import org.vsg.cusp.core.utils.ClassUtils;
import org.vsg.cusp.engine.rapidoid.specimpl.AsyncHttpRequestImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author ruanweibiao
 *
 */
public class RapidoidEngine implements ServEngine, EngineCompLoaderService , Runnable {

	private static Logger logger = LoggerFactory.getLogger(RapidoidEngineModule.class);	


	@Override
	public void start() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Engined is starting ... ");
		}

		Thread threadHook = new Thread(this);
		threadHook.start();
		
	}
	
	private Server serv;
	
	Setup setup = On.setup();		
	
	@Override
	public void run() {
		String host = arguments.get("host");
		int port = Integer.parseInt( arguments.get("port") );

		try {

			setup.address(host);
			setup.port(port);
			
			serv = setup.listen();
			

			// --- bind http service ---

			
			logger.info("listen http port : [" + port + "].");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	@Override
	public void stop() {
		try {
			setup.shutdown();

			
			if (null != serv) {
				// --- shutdown server ---
				if (serv.isActive()) {
					serv.shutdown();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	private Map<String, String> arguments;
	
	@Override
	public void init(Map<String, String> arguments) {
		// TODO Auto-generated method stub
		this.arguments = arguments;
	}
	
	private void initComponentService(File  homePath , ClassLoader clsLoader , FastHttp http) {
		
		File confFile = new File(homePath , "comp.json");
		
		if (!confFile.exists()) {
			logger.warn("Could not find \"comp.json\" file under the " + homePath);
		}
		
		Set<String> scanPackages = new LinkedHashSet<String>();
		
		String contextPath = "";
		// --- init config setting ---
		try {
			// --- scan rest api , parse json file ---
			JSONObject jObj = (JSONObject)JSON.parse( IOUtils.toString(confFile.toURI() , "utf-8") );
			
			// --- load to package scan package ---
			JSONArray scanPackageArr = jObj.getJSONArray("package-scan");
	    	for (Iterator<String> spIter =  (Iterator<String>)(Iterator)scanPackageArr.iterator() ; spIter.hasNext(); ) {
	    		String onePackage = spIter.next();
	    		scanPackages.add( onePackage);
	    	}
	    	
	    	contextPath = jObj.getString("context-path") == null ? "" : jObj.getString("context-path");
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Class<?>> allFoundCls = new Vector<Class<?>>();
		
		// --- show scan package ---
		for (String packageItem : scanPackages) {
			
			List<Class<?>> allCls =  ClassUtils.getClasses( packageItem , clsLoader , new ClassFilter() {

				@Override
				public boolean filter(Class<?> clsInput) {
					// TODO Auto-generated method stub
					Path pathMarkInClass = clsInput.getAnnotation(Path.class);
					
					Method[] methodsInCls = clsInput.getMethods();
					
					boolean markExisted = pathMarkInClass != null;
					
					for (Method method : methodsInCls) {
						Path pathMarkOnMethod = method.getAnnotation(Path.class);
						
						if (!markExisted) {
							markExisted = pathMarkOnMethod != null;
							break;
						}
					}
					return markExisted;
				}
				
			});
			
			allFoundCls.addAll( allCls );
		}
		
		
		/**
		 * inject all path for handle
		 */

		for (Class<?> cls : allFoundCls) {
			
			implementForRestPath(cls , contextPath , http);

		}


		
		
	}	

	private void implementForRestPath(Class<?> cls , String contextPath,FastHttp http) {
		Path clsPathinst = cls.getAnnotation(Path.class);
		
		String basePath = "";
		if (null != clsPathinst) {
			basePath = clsPathinst.value();
		}
		
		try {
			List<String> restPathList = new Vector<String>();
			
			// --- append set the service ---
			Object inst = cls.newInstance();
			
			
			Method[] methods = cls.getMethods();
			
			for (Method method : methods) {
				
				Path methodPathInst = method.getAnnotation(Path.class);
				
				if ( methodPathInst != null ) {
					Collection<String> httpMethods = supportdHttpMethod(method);
					
					// --- scan method parameter ---
					
					MethodParametersMetaInfo mpMetaInfo = scanParameterMetaInfo(method);
					
					
					StringBuilder fullPath = new StringBuilder();
					if (contextPath == null || contextPath.equals("")) {
						
					} else {
						fullPath.append("/").append(contextPath );
					}
					fullPath.append( basePath ).append( methodPathInst.value() );
					restPathList.add( fullPath.toString() );
					
					// --- bind get method ---
					if (httpMethods.contains( HttpMethod.GET )) {
						// --- bind request ---
						OnRoute route = new OnRoute(http, setup.defaults(), org.rapidoid.util.Constants.GET, fullPath.toString());

						route.serve(new ReqHandler() {
							private static final long serialVersionUID = 3162459205800800468L;

							@Override
							public Object execute(Req req)
									throws Exception {
								
								AsyncHttpRequestImpl hreq = new AsyncHttpRequestImpl(req);
								
								FutureTask<AsyncHttpRequestImpl> ft = new FutureTask<AsyncHttpRequestImpl>(new Callable<AsyncHttpRequestImpl>() {

									@Override
									public AsyncHttpRequestImpl call() throws Exception {
										// TODO Auto-generated method stub
										injectParameterInstanceToMethod(mpMetaInfo , hreq , fullPath.toString());
										
										Object returnVal = method.invoke(inst , mpMetaInfo.getParams());
										
										// --- close request ---
										hreq.close();
										
										return hreq;
									}

								});
								// --- add future task to manager ---
								
								// --- add future task to manager ----
								Thread runThread = new Thread(ft);
								runThread.start();								

								return ft;
								
							}
							
						});
						// --- call method ---

					}
					
					
				}
				
				// --- bind to rest api 
				
			}			

			
		} catch (SecurityException e) {
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
	
	
	private Map<String , String> parsePathParameter(String path , String pattern) {
		StringTokenizer patternToken = new StringTokenizer(pattern,"\\/");
		StringTokenizer pathToken = new StringTokenizer(path,"\\/");
		
		Map<String,String> pathParams = new LinkedHashMap<String,String>();

		while (patternToken.hasMoreElements()) {
			
			String patternVar = patternToken.nextToken();
			String pathVar = pathToken.nextToken();

			if (patternVar.indexOf("{") > -1 && patternVar.indexOf("}") > -1) {
				String paramName = patternVar.replace("{", "").replace("}", "");
				
				pathParams.put( paramName , pathVar);
			} else {
				// --- check the pattern value equat path value or not ---
				if (!patternVar.equals(pathVar)) {
					logger.warn( "patternVar=" + patternVar + " , " + "pathToken=" + pathVar );
				}
			}
	     }
		
		return pathParams;
	}
	
	
	private Collection<String> supportdHttpMethod(Method method) {
		Annotation[] annos = method.getAnnotations();
		
		Collection<String> supportedHpMethods = new Vector<String>();
		for (Annotation anno : annos) {
			if ( anno.annotationType().equals( GET.class ) ) {
				supportedHpMethods.add( HttpMethod.GET );
			}
		}
		return supportedHpMethods;
	}
	
	
	private MethodParametersMetaInfo scanParameterMetaInfo(Method method) {
		
		MethodParametersMetaInfo info = new MethodParametersMetaInfo();
		
		Parameter[] parameters = method.getParameters();

		
		info.setParamCls( method.getParameterTypes() );
		Object[] params = new Object[method.getParameterTypes().length];
		info.setParams( params );
		
		info.setParameters( parameters );
		
		return info;
	}
   
	private void injectParameterInstanceToMethod(MethodParametersMetaInfo info , AsyncHttpRequestImpl req , String patternPath) {
		
		Class<?>[] supportedClass = info.getParamCls();
		Object[] paramInsts = info.getParams();
		
		for (int i = 0 ; i < supportedClass.length ; i++) {
			Class<?> paramType = supportedClass[i];
			Object paramInst = paramInsts[i];
			
		
			if ( paramInst != null) {
				continue;
			}
			
			Req orgReq = req.getReq();
			
			Map<String , String> pathParams = parsePathParameter(orgReq.path() , patternPath);			
			
			// --- parse any class type ---
			if (paramType.equals( AsyncResponse.class )) {
				// --- build async response implement ---
				AsyncResponse inst = req.getAsyncContext().getAsyncResponse();
				paramInsts[i] = inst;
			}
			else if (paramType.isPrimitive()) {
				java.io.Serializable value = null;				
				if (info.presentAnnotation(i , PathParam.class) ) {
					PathParam pp = info.receiveAnnotationInst(i, PathParam.class);
					value = pathParams.get( pp.value() );
				};
				if (paramType.getTypeName().equals("int")) {
					paramInsts[i] = value == null ? 0 : Integer.parseInt(value.toString());
				}


			}
			else if ( paramType instanceof java.io.Serializable ) {
				java.io.Serializable value = null;
				if (info.presentAnnotation(i , PathParam.class) ) {
					PathParam pp = info.receiveAnnotationInst(i, PathParam.class);
					value = pathParams.get( pp.value() );
				};
				
				if (paramType.equals(String.class)) {
					paramInsts[i] = value.toString();
				}
			}
			

		}
		
		
	}


	@Override
	public void appendLoadService(File homePath , ClassLoader classLoader) {
		FastHttp http = setup.http();
		
		initComponentService(homePath , classLoader , http);
		
		
	}	
	
	

}
