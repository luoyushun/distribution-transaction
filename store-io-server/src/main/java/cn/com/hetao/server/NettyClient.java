package cn.com.hetao.server;

import cn.com.hetao.server.entity.NettyClientBean;
import io.netty.channel.ChannelFuture;

import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/8 19:26
 *@desc
 **/
public interface NettyClient {

    public List<NettyClientBean> connectNettyClient(String[] urls);

    public NettyClientBean connectNettyClientOne(String url);

}
