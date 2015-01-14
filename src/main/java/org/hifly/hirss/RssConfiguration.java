package org.hifly.hirss;


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RssConfiguration extends AbstractConfiguration {

    public Map<String,String> configMap = new HashMap<String,String>() {
        {
            put("binding_address", "127.0.0.1");
            put("port", "1225");
            put("max_threads", "50");
            put("rss_folder", ".");
        }
    };


    public RssConfiguration(File file) {
        super(file);
    }

    @Override
    public boolean validate() throws Exception {
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

        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            if(!configMap.containsKey(key))  //key not expected
                throw new IllegalStateException("Config file not complaint");
            if(!StringUtils.isBlank(value)) { //validate values
                validateValue(key, value);
            }
        }

        super.configMap = configMap;

        return true;
    }

    @Override
    public void configure() throws Exception {
        validate(); //validation
    }

    //TODO implement validation
    private void validateValue(String key, String value) throws Exception {
        configMap.put(key, value);
    }

    private Properties readConfigFile() throws Exception {
        Properties properties;
        FileInputStream fis = null;
        try  {
            fis = new FileInputStream(getConfigFile());
            properties = new Properties();
            properties.load(fis);
        } catch (IOException io) {
            throw new Exception(io);
        }
        finally {
           if(fis != null)
               fis.close();
        }
        return properties;
    }
}
