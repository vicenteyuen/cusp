/**
 * 
 */
package org.vsg.cusp.eventbus.codes;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.junit.Test;
import org.vsg.cusp.eventbus.spi.Buffer;

/**
 * @author Vicente Yuen
 *
 */
public class MessageCodesTest {
	
	@Test
	public void test_bytes_case1() throws Exception {
		Buffer buffer = Buffer.factory.buffer();
		
		buffer.appendBytes("hello world".getBytes() );
		buffer.appendBytes("hello world2".getBytes() );
		
		System.out.println(new String(buffer.getBytes(),"utf-8"));
		
        byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();  
        
        //下面代码是把mac地址拼装成String  
        StringBuffer sb = new StringBuffer();  
          
        for(int i=0;i<mac.length;i++){  
            if(i!=0){  
                sb.append("-");  
            }  
            //mac[i] & 0xFF 是为了把byte转化为正整数  
            String s = Integer.toHexString(mac[i] & 0xFF);  
            sb.append(s.length()==1?0+s:s);  
        }
        
        System.out.println(sb);
		
	}

}
