package cn.com.hetao.server.handler;

import cn.com.hetao.server.entity.NoticeEntity;
import io.netty.channel.ChannelHandlerContext;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/10 16:06
 *@desc 这个是处理新的数据信息
 **/
public interface DealNewDataHandler {

    public boolean dealData(NoticeEntity entity);

    public boolean dealData(ChannelHandlerContext chc, NoticeEntity entity);

}
