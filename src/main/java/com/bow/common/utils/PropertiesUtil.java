package com.bow.common.utils;

import com.bow.common.exception.ShineException;
import org.apache.commons.lang3.StringUtils;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 默认加载的properties文件
     */
    private static final String[] DEFAULT_PROPERTIES_URL = { "META-INF/default-shine.properties",
            "META-INF/channel-pool.properties" };

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
                LOGGER.error("IOException when close inputStream " + fileUrl, e);
            }
        }
    }

    public static String getProperty(String key) {
        return StringUtils.trim(properties.getProperty(key));
    }

    public static String getProperty(String key, String defaultValue) {
        return StringUtils.trim(properties.getProperty(key, defaultValue));
    }

    public static int getInt(String key, int def) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            LOGGER.warn("fail to parseInt for key: " + key);
            return def;
        }
    }

    public static long getLong(String key, long def) {
        try {
            return Long.parseLong(getProperty(key));
        } catch (NumberFormatException e) {
            LOGGER.warn("fail to parseLong for key: " + key);
            return def;
        }
    }

    public static boolean getBoolean(String key, boolean def) {
        try {
            return Boolean.parseBoolean(getProperty(key));
        } catch (NumberFormatException e) {
            LOGGER.warn("fail to parseInt for key: " + key);
            return def;
        }
    }
}
