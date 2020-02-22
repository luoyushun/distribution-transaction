package cn.com.client.hetao.handler;

import cn.com.client.hetao.config.ExecutorServiceBean;
import cn.com.client.hetao.startserver.SimpleRepeatLinkedServer;
import cn.com.common.hetao.entity.TransactionResultEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 12:44
 *@desc
 **/
public class DataDealHandler extends ChannelInboundHandlerAdapter {

    Log log = LogFactory.getLog(DataDealHandler.class);

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
//        System.out.println("与服务器断开链接");
        log.info("与服务器断开链接");
        if (SimpleRepeatLinkedServer.isLoading) return;
        SimpleRepeatLinkedServer.isLoading = true;
        final SimpleRepeatLinkedServer linkedServer = new SimpleRepeatLinkedServer();
        ExecutorServiceBean.getInstances().submit(new Runnable() {
            @Override
            public void run() {
                linkedServer.repeatLinkedServer();
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        final TransactionResultEntity definationEntity = (TransactionResultEntity) msg;
        SimpleTransactionDealDataHandler dealDataHandler = new SimpleTransactionDealDataHandler();
        dealDataHandler.dealHandler(definationEntity);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        System.out.println("断开连接");
        log.info("数据异常");
        super.exceptionCaught(ctx, cause);
    }
}
