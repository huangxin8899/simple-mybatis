package com.huangxin.io;

import java.io.InputStream;

/**
 * Resources
 *
 * @author huangxin 
 */
public class Resources {

    /**
     * 读取文件
     */
    public static InputStream getResourceAsStream(String path) {
        return ClassLoader.getSystemResourceAsStream(path);
    }
}
