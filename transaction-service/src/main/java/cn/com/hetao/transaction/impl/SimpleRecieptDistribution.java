package cn.com.hetao.transaction.impl;

import cn.com.common.hetao.enums.ReleaseStatus;
import cn.com.hetao.netty.ContainorDataDeal;
import cn.com.hetao.transaction.ReceiptDistributionAbs;
import cn.com.common.hetao.utils.IdWorker;
import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.common.hetao.enums.LockStatus;
import cn.com.hetao.transaction.deal.impl.SimpleTransactionData;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 13:37
 *@desc
 **/
@Component("simpleString")
@Slf4j
public class SimpleRecieptDistribution extends ReceiptDistributionAbs {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SimpleTransactionData simpleTransactionData;

    @Override
    public <T> void receiptDataAndDeal(ChannelHandlerContext ctx, T t) {
        String datas = null;
        if (t instanceof String) {
            datas = (String) t;
        }
        if (StringUtils.isEmpty(datas)) return;
        TransactionDefinationEntity definationEntity = JSONObject.toJavaObject(JSONObject.parseObject(datas), TransactionDefinationEntity.class);
        definationEntity.setRequestTime(new Date());
        definationEntity.setCurrentThread(Thread.currentThread());
        if (definationEntity == null) return;
        if (definationEntity.getId() == null || definationEntity.getId().longValue() <= 0) {
            definationEntity.setId(idWorker.nextId());
        }
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
        }
    }
}
