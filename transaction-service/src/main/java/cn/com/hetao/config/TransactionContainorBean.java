package cn.com.hetao.config;

import cn.com.hetao.netty.ContainorDataDeal;
import cn.com.hetao.netty.NettyClientBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 17:51
 *@desc
 **/
public class TransactionContainorBean {

    /**
     * 这个是当前服务的锁的连接表格
     */
    public static volatile ConcurrentMap<String, List<ContainorDataDeal>> dataWaitDeals = new ConcurrentHashMap<>();
    /**
     * 这个是获取锁的服务
     */
    public static volatile ConcurrentMap<String, ContainorDataDeal> dataGainDeals = new ConcurrentHashMap<>();
    /**
     * 这个是其他服务的锁的容器,这个是其他服务器获取到锁的容器
     */
    public static volatile ConcurrentMap<String, ContainorDataDeal> otherServerData = new ConcurrentHashMap<>();

    /**
     * 这个是其他服务器的发送数据的bean
     */
    public static volatile ConcurrentMap<String, NettyClientBean> nettyClinetBean = new ConcurrentHashMap<>();

    /**
     * 失联的服务
     */
    public static volatile List<String> losedServer = new ArrayList<>();

    /**
     * 服务分布式的各个服务列表，不包含自己
     */
    public static volatile List<String> servers = new ArrayList<>();

    /**
     * 静态线程池
     */
    public static volatile ExecutorService executorService = Executors.newCachedThreadPool();

}
