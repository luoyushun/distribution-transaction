package cn.com.client.hetao.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 13:26
 *@desc
 **/
public class ExecutorServiceBean {

    private static ExecutorService executorService;

    public synchronized static ExecutorService getInstances() {
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool();
        }
        return executorService;
    }

}
