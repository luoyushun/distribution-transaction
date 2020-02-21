package cn.com.hetao.instep;

import cn.com.hetao.config.TransactionContainorBean;
import cn.com.hetao.netty.DataDealHandler;
import cn.com.hetao.netty.SimpleIpPortRemoteInfos;
import cn.com.hetao.transaction.ReceiptDistributionAbs;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 12:44
 *@desc
 **/
@AllArgsConstructor
public class DataDealClientHandler extends ChannelInboundHandlerAdapter {

    private ReceiptDistributionAbs receiptDistributionAbs;

    /**
     * 这里是删除服务之间的信息的
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (ctx == null) return;
        SimpleIpPortRemoteInfos ipPortRemoteInfos = new SimpleIpPortRemoteInfos();
        String ip = ipPortRemoteInfos.getIp(ctx);
        Integer port = ipPortRemoteInfos.getPort(ctx);
        String key = ip + ":" + port.intValue();
        for (String address : TransactionContainorBean.servers) {
            if (address.equals(key)) {
                TransactionContainorBean.losedServer.add(key);
                TransactionContainorBean.nettyClinetBean.remove(key);
                break;
            }
        }
    }

    /**
     * 这里是进行通知的
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        receiptDistributionAbs.receiptDataAndDeal(ctx, msg);
    }

    /**
     * 这里是异常处理的
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        System.out.println("断开连接");
        Log log = LogFactory.getLog(DataDealHandler.class);
        log.info("数据异常");
//        super.exceptionCaught(ctx, cause);
    }
}
