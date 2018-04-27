package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码和解码工具
 * @author yuanlai
 * @date 2014-04-05
 */
public final class CodecUtils {

	private static final Logger logger = LoggerFactory.getLogger(CodecUtils.class);

	/**
	 * 将 URL 编码
	 * @param source
	 * @return
	 */
	public static String encodeURL(String source){
		String target;
		try {
			target = URLEncoder.encode(source,"UTF-8");
		}catch (Exception e){
			logger.error("encode url failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * URL 解码
	 * @param source
	 * @return
	 */
	public static String decodeURL(String source){
		String target;
		try {
			target = URLDecoder.decode(source,"UTF-8");
		}catch (Exception e){
			logger.error("decode url failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}
}
