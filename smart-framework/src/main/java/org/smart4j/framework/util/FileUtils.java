package org.smart4j.framework.util;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 文件操作工具类
 * Created by HP on 2014-04-09.
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 获取真实文件名(自动去掉文件路径)
     * @param fileName
     * @return
     */
    public static String getRealFileName(String fileName){
        return FilenameUtils.getName(fileName);
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static File createFile(String filePath){
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if(!parentDir.exists()){
                org.apache.commons.io.FileUtils.forceMkdir(parentDir);
            }
        }catch (Exception e){
            logger.error("create file failure", e);
            throw new RuntimeException(e);
        }

        return file;
    }


}
