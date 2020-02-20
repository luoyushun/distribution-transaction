package cn.com.hetao.netty;

import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.common.hetao.entity.TransactionResultEntity;
import cn.com.hetao.transaction.ReceiptDistributionAbs;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 13:44
 *@desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainorDataDeal implements Serializable {

    private TransactionDefinationEntity definationEntity;
    private ChannelHandlerContext ctx;
    private String message;
    private Integer status;
    private ReceiptDistributionAbs receiptDistributionAbs;

    public void waitForSecond() throws Exception {
        synchronized (this) {
            this.wait();
        }
        TransactionResultEntity resultEntity = new TransactionResultEntity();
        resultEntity.setId(definationEntity.getId().longValue());
        resultEntity.setMicServer(definationEntity.getMicServer());
        resultEntity.setMicServerIp(definationEntity.getMicServerIp());
        resultEntity.setResourcesId(definationEntity.getResourcesId());
        resultEntity.setResourcesName(definationEntity.getResourcesName());
        resultEntity.setLockStatus(status);
        resultEntity.setMessage(message);
        receiptDistributionAbs.sendMessage(ctx, resultEntity);
    }

    public void sendMessages(Integer status, String message) throws Exception {
        TransactionResultEntity resultEntity = new TransactionResultEntity();
        resultEntity.setId(definationEntity.getId().longValue());
        resultEntity.setMicServer(definationEntity.getMicServer());
        resultEntity.setMicServerIp(definationEntity.getMicServerIp());
        resultEntity.setResourcesId(definationEntity.getResourcesId());
        resultEntity.setResourcesName(definationEntity.getResourcesName());
        resultEntity.setLockStatus(status);
        resultEntity.setMessage(message);
        receiptDistributionAbs.sendMessage(ctx, resultEntity);
    }

    public void wakeForSecond(Integer status, String message) throws Exception {
        this.status = status;
        this.message = message;
        synchronized (this) {
            this.notify();
        }
    }

}
