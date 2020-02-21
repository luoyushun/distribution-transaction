package cn.com.hetao.instep;

import io.netty.channel.ChannelFuture;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 17:22
 *@desc
 **/
public interface ConnectionNettyClient {

    public ChannelFuture connection(String address);

    public ChannelFuture connection(String ip, Integer port);

}
