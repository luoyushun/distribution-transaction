package cn.com.hetao.controller;

import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.hetao.config.TransactionContainorBean;
import cn.com.hetao.netty.ContainorDataDeal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/22 12:23
 *@desc
 **/
@RestController
@RequestMapping("/lock")
public class TransactionServiceController {

    /**
     * 获取服务列表
     * @return
     */
    @GetMapping("/servers")
    public List<String> getServers() {
        List<String> servers = TransactionContainorBean.servers;
        return servers;
    }

    @GetMapping("/lostServers")
    public List<String> getLostServers() {
        List<String> loseServers = TransactionContainorBean.losedServer;
        return loseServers;
    }

    /**
     * 获取获取锁的资源
     * @return
     */
    @GetMapping("/gainLock")
    public List<TransactionDefinationEntity> getGainLock() {
        List<TransactionDefinationEntity> definationEntities = new ArrayList<>();
        synchronized (TransactionContainorBean.dataGainDeals) {
            for (ConcurrentMap.Entry<String, ContainorDataDeal> enty : TransactionContainorBean.dataGainDeals.entrySet()) {
                definationEntities.add(enty.getValue().getDefinationEntity());
            }
        }
        return definationEntities;
    }

    /**
     * 获取等待锁的资源
     * @return
     */
    @GetMapping("/wailtLock")
    public List<TransactionDefinationEntity> getWaitLock() {
        List<TransactionDefinationEntity> definationEntities = new ArrayList<>();
        synchronized (TransactionContainorBean.dataWaitDeals) {
            for (ConcurrentMap.Entry<String, List<ContainorDataDeal>> enty : TransactionContainorBean.dataWaitDeals.entrySet()) {
                for (ContainorDataDeal dataDeal : enty.getValue()) {
                    definationEntities.add(dataDeal.getDefinationEntity());
                }
            }
        }
        return definationEntities;
    }

}
