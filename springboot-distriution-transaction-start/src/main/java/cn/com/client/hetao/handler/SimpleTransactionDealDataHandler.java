package cn.com.client.hetao.handler;

import cn.com.client.hetao.config.DefinationDataBean;
import cn.com.client.hetao.config.ExecutorServiceBean;
import cn.com.client.hetao.entity.TransactionExtDefination;
import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.common.hetao.entity.TransactionResultEntity;
import cn.com.common.hetao.enums.LockStatus;

import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 12:08
 *@desc
 **/
public class SimpleTransactionDealDataHandler implements TransactionDealDataHandler {
    @Override
    public <T> boolean dealHandler(T t) {
        final TransactionResultEntity definationEntity = (TransactionResultEntity) t;

        ExecutorServiceBean.getInstances().submit(new Runnable() {
            @Override
            public void run() {
                TransactionResultEntity entity = definationEntity;
                if (entity == null) return;
                if (entity.getLockStatus().intValue() == LockStatus.LOCKING.value()){
                    System.out.println("等待锁");
                } else {
                    System.out.println("要开始释放锁" + entity.getId());
                    synchronized (DefinationDataBean.getInstances()) {
                        List<TransactionExtDefination> definationEntities = DefinationDataBean.getInstances().get(entity.getResourcesId());
                        if (definationEntities == null || definationEntities.isEmpty()){
                            System.out.println("数据为空");
                            return;
                        }
                        for (TransactionExtDefination exd : definationEntities) {
                            TransactionDefinationEntity e = exd.getDefinationEntity();
                            if (entity.getId().longValue() == e.getId().longValue()) {
                                e.setLockStatus(entity.getLockStatus());
                                exd.getCountDownLatch().countDown();
                            }
                        }
                    }
                }
            }
        });
//        BeanManager.executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                TransactionResultEntity entity = definationEntity;
//                if (entity == null) return;
//                List<TransactionExtDefination> definationEntities = BeanManager.definationsDatas.get(entity.getResourcesId());
//                if (definationEntities == null || definationEntities.isEmpty()){
//                    System.out.println("数据为空");
//                } else if (entity.getLockStatus().intValue() == LockStatus.LOCKING.value()){
//                    System.out.println("等待锁");
//                } else {
//                    System.out.println("要开始释放锁");
//                    synchronized (definationEntities) {
//                        for (TransactionExtDefination exd : definationEntities) {
//                            TransactionDefinationEntity e = exd.getDefinationEntity();
//                            if (entity.getId().longValue() == e.getId().longValue()) {
//                                e.setLockStatus(entity.getLockStatus());
//                                exd.getCountDownLatch().countDown();
//                            }
//                        }
//                    }
//                }
//            }
//        });
        return true;
    }
}
