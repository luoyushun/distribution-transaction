package cn.com.hetao.netty;

import cn.com.hetao.transaction.ReceiptDistributionAbs;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 12:34
 *@desc 这个是netty服务的类
 **/
@Component
@Slf4j
public class NettyService {

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    /**
     * 这个是绑定的端口
     */
    @Value("${connection.port}")
    private Integer port;

    /**
     * 这个是接收发送数据的对应的类
     */
    @Resource(name = "simpleObject")
    private ReceiptDistributionAbs receiptDistributionAbs;

    /**
     * 这个是初始化线程池
     */
    @Autowired
    private ExecutorService executorService;

    /**
     * 这个是注入清理锁的对象的数据
     */
    @Resource(name = "simpleClearLock")
    private ClearLockData clearLockData;

    /**
     * 初始化netty服务器
     */
    @PostConstruct
    public void init() {
        log.info("开始初始化netty服务");
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(); // (1)
        workerGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class) // (3)
                .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ObjectEncoder());
                        ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        ch.pipeline().addLast(new DataDealHandler(receiptDistributionAbs, clearLockData));
                    }
                });
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 2048);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ChannelFuture f = serverBootstrap.bind(port).sync(); // (7)
                    f.channel().closeFuture().sync();
                }catch (Exception e) {
                    log.error("netty启动失败");
                } finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }
            }
        });
        log.info("netty服务启动");
    }

    /**
     * 关闭服务器
     */
    public void close() {
        if (bossGroup != null && !bossGroup.isShutdown()) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null && !workerGroup.isShutdown()) {
            workerGroup.shutdownGracefully();
        }
    }

}
