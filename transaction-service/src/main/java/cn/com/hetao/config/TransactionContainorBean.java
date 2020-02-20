package cn.com.hetao.config;

import cn.com.hetao.netty.ContainorDataDeal;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 17:51
 *@desc
 **/
public class TransactionContainorBean {

    public static volatile ConcurrentMap<String, List<ContainorDataDeal>> dataWaitDeals = new ConcurrentHashMap<>();
    public static volatile ConcurrentMap<String, ContainorDataDeal> dataGainDeals = new ConcurrentHashMap<>();

}
