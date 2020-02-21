package cn.com.hetao.instep;

import cn.com.hetao.config.TransactionContainorBean;
import cn.com.hetao.netty.NettyClientBean;
import cn.com.hetao.transaction.ReceiptDistributionAbs;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 17:23
 *@desc 这个是创建客户端
 **/
@AllArgsConstructor
public class SimpleConnectionNettyClient implements ConnectionNettyClient {

    private ReceiptDistributionAbs receiptDistributionAbs;

    @Override
    public ChannelFuture connection(String address) {
        String[] addr = address.split(":");
        String ip = addr[0];
        Integer port = Integer.parseInt(addr[1]);
        return connection(ip, port.intValue());
    }

    @Override
    public ChannelFuture connection(String ip, Integer port) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap(); // (1)
        bootstrap.group(workerGroup); // (2)
        bootstrap.channel(NioSocketChannel.class); // (3)
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ObjectEncoder());
                ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                ch.pipeline().addLast(new DataDealClientHandler(receiptDistributionAbs));
            }
        });

        try {

            final String key = ip + ":" + port.intValue();
            final ChannelFuture channelFuture = bootstrap.connect(ip, port).sync(); // (5)
            TransactionContainorBean.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        channelFuture.channel().closeFuture().sync();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        workerGroup.shutdownGracefully();
                        TransactionContainorBean.nettyClinetBean.remove(key);
                    }
                }
            });
            NettyClientBean nettyClientBean = new NettyClientBean();
            nettyClientBean.setIp(ip);
            nettyClientBean.setPort(port);
            nettyClientBean.setChannelFuture(channelFuture);
            TransactionContainorBean.nettyClinetBean.put(key, nettyClientBean);
            return channelFuture;
        } catch (Exception e) {
            return null;
        }
    }
}
