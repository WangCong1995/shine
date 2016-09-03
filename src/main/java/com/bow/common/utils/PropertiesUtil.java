package com.bow.common.utils;

import com.bow.common.exception.ShineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author vv
 * @since 2016/8/19
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 默认加载的properties文件
     */
    private static final String[] DEFAULT_PROPERTIES_URL = { "META-INF/default-shine.properties" };

    private static Properties properties = new Properties();

    static {
        for (String url : DEFAULT_PROPERTIES_URL) {
            loadFile(url);
        }
    }

    private static void loadFile(String fileUrl) {
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileUrl);

        try {
            properties.load(is);
        } catch (IOException e) {
            throw new ShineException("IOException when load" + fileUrl, e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("IOException when close inputStream " + fileUrl, e);
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
