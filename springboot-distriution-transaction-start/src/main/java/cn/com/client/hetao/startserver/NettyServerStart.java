package cn.com.client.hetao.startserver;

import cn.com.client.hetao.config.ExecutorServiceBean;
import cn.com.client.hetao.config.NettyClientProperty;
import cn.com.client.hetao.handler.DataDealHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 19:35
 *@desc 这个是启动netty服务的服务器的类
 **/
public class NettyServerStart {

    private static volatile Bootstrap bootstrap;

    private static volatile String[] urls;

    public static volatile boolean isStart;

    public static volatile boolean isOver;

    private static volatile ChannelFuture channelFuture;

    private static volatile EventLoopGroup workerGroup;

    /**
     * 这里是初始化netty服务器
     */
    public static void init() {
        urls = NettyClientProperty.urls.split(";");
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap(); // (1)
        bootstrap.group(workerGroup); // (2)
        bootstrap.channel(NioSocketChannel.class); // (3)
//        bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
//                ChannelPipeline p = ch.pipeline();
//                p.addLast("decoder", new StringDecoder());
//                p.addLast("encoder", new StringEncoder());
                ch.pipeline().addLast(new ObjectEncoder());
                ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                ch.pipeline().addLast(new DataDealHandler());
            }
        });
//        connect(0);
    }

    /**
     * 这里是关闭与服务器的连接
     */
    public static void close() {
        workerGroup.shutdownGracefully();
    }

    /**
     * 这里是连接服务器的
     * @param index
     * @return
     */
    public static boolean connect(Integer index) {
        if (index >= urls.length) {
            return false;
        }
        try {
            String[] address = urls[index].split(":");
            channelFuture = bootstrap.connect(address[0], Integer.parseInt(address[1])).sync(); // (5)
            ExecutorServiceBean.getInstances().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        channelFuture.channel().closeFuture().sync();
                    }catch (Exception e) {
                        e.printStackTrace();
                        isStart = false;
                    }finally {
                        workerGroup.shutdownGracefully();
                    }
                }
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 这里是发送消息给服务器的
     * @param ts
     * @param <T>
     * @return
     */
    public static  <T> boolean sendMessage(T ts) {
//        final String message = JSONObject.toJSONString(ts);
        new Thread(new Runnable() {
            @Override
            public void run() {
                channelFuture.channel().writeAndFlush(ts);
            }
        }).start();
        return true;
    }
}
