package cn.com.client.hetao.handler;

import cn.com.client.hetao.entity.TransactionExtDefination;
import cn.com.client.hetao.startserver.NettyServerStart;
import cn.com.client.hetao.startserver.SimpleRepeatLinkedServer;
import cn.com.common.hetao.enums.LockStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.CountDownLatch;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 19:37
 *@desc
 **/
public class SimpleDealWaitDataHandler implements DealWaitDataHandler {

    Log log = LogFactory.getLog(SimpleDealWaitDataHandler.class);
    /**
     * 这里使用的递归方式来处理永久等待时，服务器断链后，进行不断第请求，直到获取相应的数据为止
     * @param extDefination
     * @return
     */
    @Override
    public boolean waitDataHandler(TransactionExtDefination extDefination) {
        log.info("这里是等待请求锁的连接");
        extDefination.setCountDownLatch(new CountDownLatch(1));
        extDefination.setRepeatRequest(false);
        NettyServerStart.sendMessage(extDefination.getDefinationEntity());
        try {
            extDefination.getCountDownLatch().await();
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (extDefination.isRepeatRequest()) {
            return waitDataHandler(extDefination);
        }
        if (extDefination.getDefinationEntity().getLockStatus().intValue() == LockStatus.SUCCESS.value().intValue()) {
            return true;
        }
        return false;
    }
}
