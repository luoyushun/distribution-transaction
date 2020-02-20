package cn.com.hetao.transaction;

import io.netty.channel.ChannelHandlerContext;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 13:29
 *@desc
 **/
public interface ReceiptDistribution {

    public <T> void receiptDataAndDeal(ChannelHandlerContext ctx, T t);

}
