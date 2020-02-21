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
 *@desc 这个是锁的bean对象，里面存储了请求锁的对象，channel通道对象，发送给客户端的信息和状态，以及发送信息的类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainorDataDeal implements Serializable {

    /**
     * 锁对象
     */
    private TransactionDefinationEntity definationEntity;
    /**
     * 通道对象
     */
    private ChannelHandlerContext ctx;
    /**
     * 要发送的信息
     */
    private String message;
    /**
     * 发送信息的状态
     */
    private Integer status;
    /**
     * 发送信息的类
     */
    private ReceiptDistributionAbs receiptDistributionAbs;

    /**
     * 发送等待信息
     * @throws Exception
     */
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

    /**
     * 发送信息
     * @param status
     * @param message
     * @throws Exception
     */
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

    /**
     * 对锁进行唤醒
     * @param status
     * @param message
     * @throws Exception
     */
    public void wakeForSecond(Integer status, String message) throws Exception {
        this.status = status;
        this.message = message;
        synchronized (this) {
            this.notify();
        }
    }

}
