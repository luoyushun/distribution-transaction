package cn.com.client.hetao.config;

import cn.com.client.hetao.entity.TransactionExtDefination;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 13:22
 *@desc
 **/
public class DefinationDataBean {

    private static ConcurrentMap<String, List<TransactionExtDefination>> definationsDatas;

    public static synchronized ConcurrentMap<String, List<TransactionExtDefination>> getInstances() {
        if (definationsDatas == null) {
            definationsDatas = new ConcurrentHashMap<>();
        }
        return definationsDatas;
    }

}
