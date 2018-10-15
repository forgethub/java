package secret;
import java.io.UnsupportedEncodingException;
//import java.math.BigInteger;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
 
public class EncryptionSha {
	public static final String KEY_SHA = "SHA";
	public static final int salt_length=15;
	public static final String ra="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
	/**
	 * 瀛楃涓瞫ha鍔犲瘑
	 * @param s 瑕佸姞瀵嗙殑瀛楃涓�
	 * @return 鍔犲瘑鍚庣殑瀛楃涓�
	 */
	public static String sha(String s)
	{
	    BigInteger sha =null;
	    byte[] bys = s.getBytes();   
	    try {
	         MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);  
	         messageDigest.update(bys);
	         sha = new BigInteger(messageDigest.digest());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return sha.toString(32);
	}
	
	/**
	 * 瀛楃涓�+闅忔満鐩� sha鍔犲瘑
	 * @param s 瑕佸姞瀵嗙殑瀛楃涓�
	 * @return 鐩愬拰鍔犲瘑鍚庣殑瀛楃涓�
	 * @throws UnsupportedEncodingException 
	 */	
	public static Map<String,String> getResult(String s) throws UnsupportedEncodingException{
		Map<String,String> map=new HashMap<String,String>();
		String salt=getSalt();
		String salt1 = ReturnSaltAndKey(salt);
		map.put("salt", salt1);//鐩�
		map.put("password", sha(s+salt));//鍔犲瘑鍚庣殑瀵嗙爜
 	    return map;
	}
	
	/**
	 * 鐢熸垚闅忔満鐩�
	 * @return 闅忔満鐢熸垚鐨勭洂
	 */
	private static String getSalt() {
		SecureRandom random;
		char[] text = null;
		try {					
			random = new SecureRandom();
			int length=random.nextInt(5)+salt_length;//鐩愮殑闀垮害锛岃繖閲屾槸8-12鍙嚜琛岃皟鏁�
	        text = new char[length];
	        for (int i = 0; i < length; i++) {
	            text[i] = ra.charAt(random.nextInt(ra.length()));
	        }
	        //return new String(text);
	    } catch (Exception e) {
	    	 e.printStackTrace(); 
		} 
        return new String(text);
    }
	
	private static String ReturnSaltAndKey(String str) throws UnsupportedEncodingException {
		return SelfBase64.toCodingBase64(str);
	}
}