package cn.com.hetao.server.impl;

import cn.com.hetao.property.StoreProperty;
import cn.com.hetao.server.NettyServer;
import cn.com.hetao.server.handler.DealNewDataHandlerSimple;
import cn.com.hetao.server.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/8 19:36
 *@desc 这个是服务器
 **/
@Component
public class NettyServerSimple implements NettyServer {

    @Resource
    private DealNewDataHandlerSimple simple;

    @Resource
    private StoreProperty storeProperty;

    @Resource
    private NettyClinetSimple nettyClient;

    @Override
    public void startServer(String bindUrl, Integer port) {
        if (storeProperty.isEnableServer()) {
            ServerBootstrap bootstrap = new ServerBootstrap();
            EventLoopGroup work = new NioEventLoopGroup();
            EventLoopGroup boss = new NioEventLoopGroup();
            bootstrap.group(boss, work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                    ch.pipeline().addLast(new NettyServerHandler(simple, nettyClient));
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG, 2048);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            final ChannelFuture f = bootstrap.bind(bindUrl, port);
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
        }
    }
}
