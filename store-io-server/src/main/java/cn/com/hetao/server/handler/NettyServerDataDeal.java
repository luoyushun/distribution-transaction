package cn.com.hetao.server.handler;

import cn.com.hetao.server.entity.NoticeEntity;
import io.netty.channel.ChannelHandlerContext;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/9 19:18
 *@desc 这个是数据处理类
 **/
public interface NettyServerDataDeal {

    /**
     * 这个是处理数据的类
     * @param chc
     * @param entity
     */
    public void dataDeal(ChannelHandlerContext chc, NoticeEntity entity);

}
