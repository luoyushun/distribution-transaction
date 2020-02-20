package cn.com.hetao.transaction.schedules;

import cn.com.hetao.config.TransactionContainorBean;
import cn.com.common.hetao.enums.ReleaseStatus;
import cn.com.hetao.netty.ContainorDataDeal;
import cn.com.hetao.transaction.deal.impl.SimpleTransactionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 16:43
 *@desc
 **/
@Component
public class ScheduleResourceDef {

    @Autowired
    private SimpleTransactionData simpleTransactionData;

    @Scheduled(fixedRate = 3000)
    public void scanTimeoutResources() {
//        System.out.println("请求");
        List<ContainorDataDeal> timeouts = new ArrayList<>();
        try {
            synchronized (TransactionContainorBean.dataWaitDeals) {
                for (ConcurrentMap.Entry entry : TransactionContainorBean.dataWaitDeals.entrySet()) {
                    if (entry == null) continue;
                    List<ContainorDataDeal> result = (List<ContainorDataDeal>) entry.getValue();
                    if (result == null || result.isEmpty()) continue;
                    synchronized (result) {
                        for (ContainorDataDeal deal :
                                result) {
                            if (deal.getDefinationEntity().getTimeout().longValue() <= 0) continue;
                            Long time = System.currentTimeMillis() - deal.getDefinationEntity().getRequestTime().getTime() - deal.getDefinationEntity().getTimeout().longValue();
                            if (time <= 0) {
                                timeouts.add(deal);
                            } else {
                                // 提高对应处理器的优先级，或者是权限
                                deal.getDefinationEntity().setPriority(deal.getDefinationEntity().getStepPriority() + deal.getDefinationEntity().getPriority());
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("操作导致指针异常问题");
        }finally {
            for (ContainorDataDeal dataDeal : timeouts) {
                try {
                    simpleTransactionData.releaseResource(dataDeal, ReleaseStatus.TIMEOUT.value());
                }catch (Exception e1) {
                    System.out.println("操作有误");
                }
            }
        }
    }

}
