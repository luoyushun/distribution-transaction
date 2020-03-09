package cn.com.hetao.server.handler;

import cn.com.hetao.server.entity.NoticeEntity;
import cn.com.hetao.server.enums.NoticeStartEnum;
import cn.com.hetao.server.event.NoticeEventInner;
import cn.com.hetao.server.event.NoticeEventInnerSimple;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/8 19:35
 *@desc
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof NoticeEntity) {
            NoticeEntity entity = (NoticeEntity) msg;
            // 这个是返回值的
            if (entity.getIsStart() == NoticeStartEnum.RESULT) {
                NoticeEventInner inner = new NoticeEventInnerSimple();
                inner.noticeEvent(entity);
            }
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
