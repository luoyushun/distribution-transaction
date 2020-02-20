package cn.com.client.hetao.handler;

import cn.com.common.hetao.entity.TransactionResultEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 12:44
 *@desc
 **/
public class DataDealHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        final TransactionResultEntity definationEntity = (TransactionResultEntity) msg;
        SimpleTransactionDealDataHandler dealDataHandler = new SimpleTransactionDealDataHandler();
        dealDataHandler.dealHandler(definationEntity);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("断开连接");
        super.exceptionCaught(ctx, cause);
    }
}
