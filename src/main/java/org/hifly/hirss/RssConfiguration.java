package org.hifly.hirss;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RssConfiguration {

    protected Map<String,String> configMap = new HashMap<String,String>() {
        {
            put("binding_address", "127.0.0.1");
            put("port", "1225");
            put("max_threads", "50");
            put("rss_folder", ".hirss/rss");
            put("rss_update_period","5000");
            put("rss_connect_timeout","25000");
        }
    };


    public RssConfiguration() {}

    public void validate() throws Exception {
        Properties properties;
        try {
            properties = readConfigFile();    //read property file
        } catch (IOException io) {
            throw new Exception(io);
        }

        int keysSize = configMap.size();
        int propsKeysSize = properties.size();
        if (keysSize != propsKeysSize) //invalid number of keys
            throw new IllegalStateException("Config file not complaint");

        Enumeration<Object> enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            if(!configMap.containsKey(key))  //key not expected
                throw new IllegalStateException("Config file not complaint");
            if(!StringUtils.isBlank(value)) { //validate values
                validateValue(key, value);
            }
        }

    }

    public void configure() throws Exception {
        validate(); //validation
    }

    //TODO implement validation
    private void validateValue(String key, String value) throws Exception {
        configMap.put(key, value);
    }

    private Properties readConfigFile() throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("hirss.properties");
        Properties properties;
        try  {
            properties = new Properties();
            properties.load(is);
        } catch (IOException io) {
            throw new Exception(io);
        }
        finally {
           if(is != null)
               is.close();
        }
        return properties;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

}
