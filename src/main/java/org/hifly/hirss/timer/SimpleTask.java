package org.hifly.hirss.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.TimerTask;

public class SimpleTask extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(SimpleTask.class);

    private final Method method;
    private final Object[] args;
    private final String key;

    public SimpleTask(Method method, Object[] args, String key) {
        this.method = method;
        this.args = args;
        this.key = key;
    }

    public void run() {
        try {
            log.info("Scheduling " + key + " - " + method.getName() + " at:" + new Date());
            method.invoke(null, args);
        } catch (Exception ex) {
            //TODO define Exception
            ex.printStackTrace();

        }
    }
}