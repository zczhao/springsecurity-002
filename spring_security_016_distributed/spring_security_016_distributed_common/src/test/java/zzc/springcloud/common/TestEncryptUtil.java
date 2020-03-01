package zzc.springcloud.common;

import zzc.springcloud.common.utils.EncryptUtil;

public class TestEncryptUtil {

	public static void main(String[] args) {
		String str = "中国武汉加油";
		String encodeStr = EncryptUtil.encodeUTF8StringBase64(str);
		System.out.println(encodeStr);
		String token = EncryptUtil.decodeUTF8StringBase64(encodeStr);
		System.out.println(token);
	}

}
