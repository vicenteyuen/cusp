/**
 * 
 */
package org.vsg.cusp.core.utils;

import java.util.Calendar;

/**
 * @author Vicente Yuen
 *
 */
public class CommonUtils {
	
	public static String getUid(byte[] clientDefMac) {
        int uaLocationInd = 1, curLoc = 0, tmpUaLength;
        for (uaLocationInd = 0, tmpUaLength = clientDefMac.length - 1; 0 <= tmpUaLength; tmpUaLength--) {
            curLoc = clientDefMac[tmpUaLength];
            uaLocationInd = (uaLocationInd << 6 & 268435455) + curLoc + (curLoc << 14);
            curLoc = uaLocationInd & 266338304;
            uaLocationInd = 0 != curLoc ? uaLocationInd ^ curLoc >> 21 : uaLocationInd;
        }
       double tmpRandomKey = Math.round(2147483647 * Math.random());


       long prefix = Double.doubleToRawLongBits(tmpRandomKey) | uaLocationInd & 2147483647;
       long suffix = Math.round(Calendar.getInstance().getTimeInMillis() / 1E3);
       StringBuilder keyStr = new StringBuilder();
       keyStr.append(prefix );
       keyStr.append(".");
       keyStr.append( suffix );
       
       return keyStr.toString();

	}
	

}
