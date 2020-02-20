package cn.com.client.hetao.config;

import cn.com.common.hetao.utils.IdWorker;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 13:25
 *@desc
 **/
public class IdWorkerBean {

    private static IdWorker idWorker;

    public synchronized static IdWorker getInstances() {
        if (idWorker == null) {
            idWorker = new IdWorker(1,1,1);
        }
        return idWorker;
    }

}
