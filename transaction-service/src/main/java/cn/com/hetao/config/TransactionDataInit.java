package cn.com.hetao.config;

import cn.com.hetao.instep.SimpleConnectionNettyClient;
import cn.com.hetao.transaction.ReceiptDistributionAbs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 15:14
 *@desc
 **/
@Configuration
@Slf4j
public class TransactionDataInit implements InitializingBean {
    /**
     * 进行初始化线程数量，线程数量和cup核数的两倍
     * @throws Exception
     */


    @Value("${connection.cluster.enable:false}")
    public boolean isClusters;

    @Value("${connection.cluster.addresses}")
    public String addresses;

    @Resource(name = "simpleObject")
    private ReceiptDistributionAbs receiptDistributionAbs;

    @Override
    public void afterPropertiesSet() throws Exception {
        initCluster();
        initThread();
    }

    private void initThread() {
        int cupNum = Runtime.getRuntime().availableProcessors();
        log.info("初始化程序");
        /**
         * 这里是初始化线程数量
         */
        if (cupNum > 0) {
            log.info("初始化线程");
            for (int i=0; i< cupNum; i++) {
                TaskExecutorsEntity taskExecutorsEntity = new TaskExecutorsEntity();
                taskExecutorsEntity.setReceiptDistributionAbs(receiptDistributionAbs);
                TransactionContainorBean.taskExecutorsEntities.add(taskExecutorsEntity);
                TransactionContainorBean.executorService.submit(taskExecutorsEntity);
            }
            log.info("初始化线程完成");
        }
    }

    private void initCluster() {
        if (!isClusters) return;
        log.info("初始化集群");
        String[] addrs = addresses.split(";");
        for (String add : addrs) {
            TransactionContainorBean.servers.add(add);
        }
        for (String address : TransactionContainorBean.servers) {
            SimpleConnectionNettyClient simpleConnectionNettyClient = new SimpleConnectionNettyClient(receiptDistributionAbs);
            simpleConnectionNettyClient.connection(address);
        }
        log.info("初始化集群完成");
    }
}
