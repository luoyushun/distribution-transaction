package cn.com.hetao.netty;

import cn.com.hetao.transaction.ReceiptDistributionAbs;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutorService;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 12:44
 *@desc
 **/
@AllArgsConstructor
public class DataDealHandler extends ChannelInboundHandlerAdapter {

    private ReceiptDistributionAbs receiptDistributionAbs;
    private ClearLockData clearLockData;

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        super.handlerRemoved(ctx);
        SimpleIpPortRemoteInfos ipPortRemoteInfos = new SimpleIpPortRemoteInfos();
        final String ip = ipPortRemoteInfos.getIp(ctx);
        final Integer port = ipPortRemoteInfos.getPort(ctx);
        clearLockData.clearLockAndGainLock(ip, port);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        receiptDistributionAbs.receiptDataAndDeal(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("断开连接");
        super.exceptionCaught(ctx, cause);
    }
}
