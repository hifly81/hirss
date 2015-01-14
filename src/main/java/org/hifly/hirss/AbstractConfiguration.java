package org.hifly.hirss;


import java.io.File;
import java.util.Map;

public abstract class AbstractConfiguration {

    protected File configFile;
    protected Map<String, String> configMap;

    public AbstractConfiguration(File file) {
         this.configFile = file;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public File getConfigFile() {
        return configFile;
    }

    public abstract boolean validate() throws Exception;

    public abstract void configure() throws Exception;

}
