package cn.com.hetao.server.handler;

import cn.com.hetao.server.entity.NoticeEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/8 19:33
 *@desc 这个是处理数据的
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private DealNewDataHandlerSimple simple;

    public NettyServerHandler(DealNewDataHandlerSimple simple) {
        this.simple = simple;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       // 这个是来处理数据的，处理数据的
        if (msg instanceof NoticeEntity) {
            NettyServerDataDeal dataDeal = new NettyServerDataDealSimple(simple);
            dataDeal.dataDeal(ctx, (NoticeEntity) msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
