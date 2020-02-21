package cn.com.hetao.transaction.impl;

import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.common.hetao.enums.InStepStatus;
import cn.com.common.hetao.enums.LockStatus;
import cn.com.common.hetao.enums.ReleaseStatus;
import cn.com.common.hetao.utils.IdWorker;
import cn.com.hetao.config.TransactionContainorBean;
import cn.com.hetao.netty.ContainorDataDeal;
import cn.com.hetao.netty.SimpleIpPortRemoteInfos;
import cn.com.hetao.transaction.ReceiptDistributionAbs;
import cn.com.hetao.transaction.deal.impl.SimpleTransactionData;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 13:37
 *@desc
 **/
@Component("simpleObject")
@Slf4j
public class SimpleObjectRecieptDistribution extends ReceiptDistributionAbs {

    @Autowired
    private SimpleTransactionData simpleTransactionData;

    private SimpleIpPortRemoteInfos remoteInfos = new SimpleIpPortRemoteInfos();

    @Override
    public <T> void receiptDataAndDeal(ChannelHandlerContext ctx, T t) {
        TransactionDefinationEntity definationEntity = null;
        if (t instanceof TransactionDefinationEntity) {
            definationEntity = (TransactionDefinationEntity) t;
        }
        if (definationEntity == null) return;
        definationEntity.setRequestTime(new Date());
        definationEntity.setMicServerIp(remoteInfos.getIp(ctx));
        definationEntity.setMicServerPort(remoteInfos.getPort(ctx));
        final ContainorDataDeal containorDataDeal = new ContainorDataDeal();
        containorDataDeal.setCtx(ctx);
        containorDataDeal.setDefinationEntity(definationEntity);
        containorDataDeal.setReceiptDistributionAbs(this);
        if (definationEntity.getLockStatus().intValue() == LockStatus.REQUEST.value().intValue()) {
            log.info("请求锁");
            simpleTransactionData.requestResource(containorDataDeal);
        } else if (definationEntity.getLockStatus().intValue() == LockStatus.CANCEL.value().intValue()){
            try {
                log.info("释放锁");
                simpleTransactionData.releaseResource(containorDataDeal, ReleaseStatus.NORMAL.value());
            }catch (Exception e) {
                try {
                    containorDataDeal.sendMessages(LockStatus.FAILURE.value(), "请求加锁失败");
                }catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } else if (definationEntity.getLockStatus().intValue() == LockStatus.INSTEP.value().intValue()) {
            // 这里是进行同步信息的
            //这里是申请获取权限的
            if (definationEntity.getInStepStatus().intValue() == InStepStatus.RELEASE.value().intValue()) {
                try {
                    simpleTransactionData.findGainResource(containorDataDeal);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
