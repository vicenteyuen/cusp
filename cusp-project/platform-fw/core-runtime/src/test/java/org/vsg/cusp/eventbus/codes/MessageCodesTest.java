/**
 * 
 */
package org.vsg.cusp.eventbus.codes;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.junit.Test;
import org.vsg.cusp.core.Buffer;
import org.vsg.cusp.core.utils.ByteUtils;

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
	
	@Test
	public void test_float_case01() throws Exception {
		float a = 1234.56f;


		byte[] floatValue =  ByteUtils.float2byte(a);
		
		float act = ByteUtils.byte2float( floatValue , 0);
		
		System.out.println(act);

	}
	
	@Test
	public void test_double_case01() throws Exception {
		double aa = 123456.789;
		
		byte[] doubleValue = ByteUtils.double2byte(aa);
		double act = ByteUtils.byte2double( doubleValue );
		System.out.println("act : " + act);
	}
	
	
}
