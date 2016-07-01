/**
 * 
 */
package org.vsg.cusp.core.utils;

import java.io.UnsupportedEncodingException;

/**
 * @author Vicente Yuen
 *
 */
public class CorrelationIdGenerator {
	
	
	private static CorrelationIdGenerator _inst = new CorrelationIdGenerator();
	

	public long generate(long timestamp) {
		return System.nanoTime();
	}
	
	public static CorrelationIdGenerator genInstance(byte[] content) {
		
		try {
			String parentKey = new String(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _inst;
	}
	
	
	
	

}
