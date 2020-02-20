package cn.com.client.hetao.startserver;

import cn.com.client.hetao.config.DefinationDataBean;
import cn.com.client.hetao.config.NettyClientProperty;
import cn.com.client.hetao.entity.TransactionExtDefination;
import cn.com.common.hetao.enums.ClientLockDeal;
import cn.com.common.hetao.enums.LockStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 19:12
 *@desc
 **/
public class SimpleRepeatLinkedServer implements RepeatLinkedServer {

    Log log = LogFactory.getLog(SimpleRepeatLinkedServer.class);

    @Override
    public void repeatLinkedServer() {
        String[] urls = NettyClientProperty.urls.split(";");
        while (true) {
            int i = 0;
            boolean isStart = false;
            for (String url: urls) {
                NettyServerStart.init();
                isStart = NettyServerStart.connect(i);
                if (isStart) break;
                NettyServerStart.close();
                i++;
            }
            if (isStart) break;
            dealLockServer(ClientLockDeal.CLEARLOCK.value().intValue());
            log.info("释放所有要求锁的资源");
            try{
                Thread.sleep(10000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        dealLockServer(ClientLockDeal.REPEATREQUEST.value().intValue());
        log.info("重新去申请占用资源");
    }

    @Override
    public void dealLockServer(Integer status) {
        if (status.intValue() == ClientLockDeal.CLEARLOCK.value().intValue()) {
            synchronized (DefinationDataBean.getInstances()) {
                for (ConcurrentMap.Entry<String, List<TransactionExtDefination>> entry : DefinationDataBean.getInstances().entrySet()) {
                    List<TransactionExtDefination> definations = entry.getValue();
                    if (definations == null || definations.isEmpty()) continue;
                    synchronized (definations) {
                        for (TransactionExtDefination extDefination : definations) {
                            extDefination.setRepeatRequest(false);
                            extDefination.getDefinationEntity().setLockStatus(LockStatus.FAILURE.value().intValue());
                            extDefination.getCountDownLatch().countDown();
                        }
                    }
                }
            }
        } else if (status.intValue() == ClientLockDeal.REPEATREQUEST.value().intValue()) {
            synchronized (DefinationDataBean.getInstances()) {
                for (ConcurrentMap.Entry<String, List<TransactionExtDefination>> entry : DefinationDataBean.getInstances().entrySet()) {
                    List<TransactionExtDefination> definations = entry.getValue();
                    if (definations == null || definations.isEmpty()) continue;
                    synchronized (definations) {
                        for (TransactionExtDefination extDefination : definations) {
                            extDefination.setRepeatRequest(true);
                            extDefination.getCountDownLatch().countDown();
                        }
                    }
                }
            }
        }
    }
}
