package cn.com.hetao.netty;

import cn.com.hetao.transaction.ReceiptDistributionAbs;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 12:44
 *@desc
 **/
@AllArgsConstructor
public class DataDealHandler extends ChannelInboundHandlerAdapter {

    private ReceiptDistributionAbs receiptDistributionAbs;

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
