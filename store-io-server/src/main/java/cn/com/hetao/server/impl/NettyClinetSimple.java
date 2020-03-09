package cn.com.hetao.server.impl;

import cn.com.hetao.server.NettyClient;
import cn.com.hetao.server.entity.NettyClientBean;
import cn.com.hetao.server.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.ArrayList;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/8 19:30
 *@desc netty 客户端的实现
 **/
public class NettyClinetSimple implements NettyClient {

    @Override
    public List<NettyClientBean> connectNettyClient(String[] urls) {
        List<NettyClientBean> beans = new ArrayList<>();
        for (String url : urls) {
            NettyClientBean bean = connectNettyClientOne(url);
            if (bean == null) continue;
            beans.add(bean);
        }

        return null;
    }

    @Override
    public NettyClientBean connectNettyClientOne(String url) {
        String[] urlD = url.split(":");
        String ip = urlD[0];
        Integer port = Integer.parseInt(urlD[1]);
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        bootstrap.group(boss);
        bootstrap.channel(NioSocketChannel.class);
        try {
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                    ch.pipeline().addLast(new NettyClientHandler());
                }
            });
            final ChannelFuture f = bootstrap.connect(ip, port).sync();
            NettyClientBean bean = new NettyClientBean();
            bean.setUrl(url);
            bean.setFuture(f);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        f.channel().closeFuture().sync();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return bean;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
