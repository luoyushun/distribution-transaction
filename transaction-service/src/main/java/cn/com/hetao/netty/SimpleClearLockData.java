package cn.com.hetao.netty;

import cn.com.hetao.config.ContainorConfig;
import cn.com.hetao.config.TransactionContainorBean;
import cn.com.hetao.transaction.deal.TransactionDataDealAbs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 17:51
 *@desc
 **/
@Component("simpleClearLock")
@Slf4j
public class SimpleClearLockData implements ClearLockData {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private TransactionDataDealAbs transactionDataDealAbs;

    @Override
    public void clearLockAndGainLock(String ip, Integer port) {
        log.info("ip=" + ip + " port = "+ port +" 主机断线了 ");
        /**
         * 处理等待队列中的数据
         */
        synchronized (TransactionContainorBean.dataWaitDeals) {
            for (ConcurrentMap.Entry<String, List<ContainorDataDeal>> entry : TransactionContainorBean.dataWaitDeals.entrySet()) {
                List<ContainorDataDeal> dataDeal = entry.getValue();
                if (dataDeal == null || dataDeal.isEmpty()) continue;
                synchronized (dataDeal) {
                    for (int i = 0; i< dataDeal.size(); i++) {
                        ContainorDataDeal deal = dataDeal.get(i);
                        if (deal.getDefinationEntity().getMicServerIp().equals(ip)
                                && deal.getDefinationEntity().getMicServerPort().intValue() == port.intValue()) {
                            dataDeal.remove(i);
                            log.info("将数据剔除等待队列====" + deal.getDefinationEntity().getResourcesId() + " id = " + deal.getDefinationEntity().getId().longValue());
                            i--;
                        }
                    }
                }
            }
        }
        /**
         * 这里是处理已经在
         */
        synchronized (TransactionContainorBean.dataGainDeals) {
            List<ContainorDataDeal> removes = new ArrayList<>();
            for (ConcurrentMap.Entry<String, ContainorDataDeal> entry : TransactionContainorBean.dataGainDeals.entrySet()) {
                ContainorDataDeal dataDeal = entry.getValue();
                if (dataDeal.getDefinationEntity().getMicServerIp().equals(ip)
                        && dataDeal.getDefinationEntity().getMicServerPort().intValue() == port.intValue()) {
                    removes.add(dataDeal);
                }
            }
            for (ContainorDataDeal deal: removes) {
                TransactionContainorBean.dataGainDeals.remove(deal.getDefinationEntity().getResourcesId());
                log.info("已经删除了数据 " + deal.getDefinationEntity().getResourcesId() + deal.getDefinationEntity().getId());
                try {
                    transactionDataDealAbs.findGainResource(deal);
                }catch (Exception e) {
                    log.info("剔除失败，请手动剔除" + deal.getDefinationEntity().getResourcesId() + deal.getDefinationEntity().getId());
                }
            }
        }
    }
}
