package cn.com.client.hetao.startserver;

import cn.com.client.hetao.config.DefinationDataBean;
import cn.com.client.hetao.config.IdWorkerBean;
import cn.com.client.hetao.config.NettyClientProperty;
import cn.com.client.hetao.entity.TransactionExtDefination;
import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.common.hetao.enums.LockStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 20:40
 *@desc
 **/
public class SimpleNettyServerDeal implements NettyServerDeal {

    @Override
    public boolean lock(String key, String desc) {
        if (NettyClientProperty.priority == null || NettyClientProperty.priority.intValue() == 0) {
            NettyClientProperty.priority = 30;
        }
        return lock(key, desc, 0L, NettyClientProperty.priority);
    }

    @Override
    public boolean lock(String key, String desc, Long timeout) {
        if (NettyClientProperty.priority == null || NettyClientProperty.priority.intValue() == 0) {
            NettyClientProperty.priority = 30;
        }
        return lock(key, desc, timeout, NettyClientProperty.priority);
    }

    @Override
    public boolean lock(String key, String desc, Integer priority) {
        return lock(key, desc, 0L, priority);
    }

    @Override
    public boolean lock(String key, String desc, Long timeout, Integer priority) {
        TransactionDefinationEntity entity = new TransactionDefinationEntity();
        entity.setId(IdWorkerBean.getInstances().nextId());
        entity.setTimeout(timeout);
        entity.setPriority(priority);
        entity.setResourcesId(key);
        entity.setResourcesName(desc);
        entity.setTimes(1);
        entity.setLockStatus(LockStatus.REQUEST.value());
        entity.setMicServer(NettyClientProperty.applicationName == null ? "default" : NettyClientProperty.applicationName);
        TransactionExtDefination extDefination = new TransactionExtDefination();
        extDefination.setThread(Thread.currentThread());
        extDefination.setCountDownLatch(new CountDownLatch(1));
        extDefination.setDefinationEntity(entity);
//        entity.setCurrentThread(Thread.currentThread());
        boolean c = false;
        synchronized (DefinationDataBean.getInstances()) {
            List<TransactionExtDefination> entities = DefinationDataBean.getInstances().get(key);
            if (entities != null) {
                synchronized (entities) {
                    for (TransactionExtDefination ted : entities) {
                        TransactionDefinationEntity e = ted.getDefinationEntity();
                        if (e.getResourcesId().equals(entity.getResourcesId()) && extDefination.getThread() == ted.getThread()) {
                            e.setTimes(e.getTimes().intValue() + 1);
                            c = true;
                        }
                    }
                    if (!c) {
                        entities.add(extDefination);
                        DefinationDataBean.getInstances().put(entity.getResourcesId(), entities);
                        System.out.println("size ====" + entities.size() + "    " + extDefination.getDefinationEntity().getId());
                        NettyServerStart.sendMessage(entity);
                    }
                }
            } else {
                entities = new ArrayList<>();
                entities.add(extDefination);
                DefinationDataBean.getInstances().put(entity.getResourcesId(), entities);
                NettyServerStart.sendMessage(entity);
            }
        }
        synchronized (extDefination) {
            try {
                if (!c) {
//                    entity.wait();
                    extDefination.getCountDownLatch().await();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (entity.getLockStatus().intValue() == LockStatus.SUCCESS.value().intValue()) {
            return true;
        }
//        if (entity.getLockStatus() == LockStatus.LOCKING.value()) {
//            synchronized (entity) {
//                try {
//                    if (!c) {
//                        entity.wait();
//                    }
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        unLock(entity.getResourcesId());
        return c;
    }

    @Override
    public boolean unLock(String key) {
        Thread thread = Thread.currentThread();
        synchronized (DefinationDataBean.getInstances()) {
            List<TransactionExtDefination> entities = DefinationDataBean.getInstances().get(key);
            if (entities == null || entities.isEmpty()) return true;
            int index = 0;
            for (TransactionExtDefination extDefination : entities) {
                TransactionDefinationEntity entity = extDefination.getDefinationEntity();
                if (entity.getResourcesId().equals(key) && thread == extDefination.getThread()) {
                    if (entity.getTimes().intValue() > 1) {
                        entity.setTimes(entity.getTimes() - 1);
                        DefinationDataBean.getInstances().put(entity.getResourcesId(), entities);
                        break;
                    }else{
                        entity.setLockStatus(LockStatus.CANCEL.value());
                        entity.setCurrentThread(null);
                        NettyServerStart.sendMessage(entity);
                        entities.remove(index);
                        System.out.println("释放锁   " + entity.getId());
                        DefinationDataBean.getInstances().put(entity.getResourcesId(), entities);
                        break;
                    }
                }
                index ++;
            }
        }
        return true;
    }
}
