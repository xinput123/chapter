package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 流操作工具类
 * @author yuanlai
 * @date 2014-04-05
 */
public class StreamUtils {

	private static final Logger logger = LoggerFactory.getLogger(StreamUtils.class);

	public static String getString(InputStream is){
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ( null!=(line=reader.readLine()) ){
				sb.append(line);
			}
		}catch (Exception e){
			logger.error("get string failure", e);
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	/**
	 * 将输入流复制到输出流
	 * @param inputStream
	 * @param outputStream
     */
	public static void copyStream(InputStream inputStream, OutputStream outputStream){
        try {
			int length;
			byte[] buffer = new byte[4 * 1024];
			while ((length=inputStream.read(buffer,0,buffer.length))!=-1 ){
				outputStream.write(buffer,0,length);
			}
			outputStream.flush();
		}catch (Exception e){
			logger.error("copy stream failure", e);
			throw new RuntimeException(e);
		}finally {
            try {
				inputStream.close();
				outputStream.close();
			}catch (Exception e){
				logger.error("close stream failure", e);
			}
		}
	}
}
