package cn.com.hetao.netty;

import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.hetao.config.TaskExecutorsEntity;
import cn.com.hetao.config.TransactionContainorBean;
import cn.com.hetao.instep.SimpleConnectionNettyClient;
import cn.com.hetao.transaction.ReceiptDistributionAbs;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutorService;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 12:44
 *@desc 这个是处理netty客户端连接后，接收数据的类
 **/
@AllArgsConstructor
public class DataDealHandler extends ChannelInboundHandlerAdapter {

    private ReceiptDistributionAbs receiptDistributionAbs;
    private ClearLockData clearLockData;

    /**
     * 客户端断开连接后，要处理的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        SimpleIpPortRemoteInfos ipPortRemoteInfos = new SimpleIpPortRemoteInfos();
        final String ip = ipPortRemoteInfos.getIp(ctx);
        final Integer port = ipPortRemoteInfos.getPort(ctx);
        String key = ip + ":" + port.intValue();
        NettyClientBean clientBean = TransactionContainorBean.nettyClinetBean.get(key);
        if (clientBean != null) {
            TransactionContainorBean.losedServer.add(key);
            TransactionContainorBean.nettyClinetBean.remove(key);
        } else {
            clearLockData.clearLockAndGainLock(ip, port);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        SimpleIpPortRemoteInfos ipPortRemoteInfos = new SimpleIpPortRemoteInfos();
        final String ip = ipPortRemoteInfos.getIp(ctx);
        final Integer port = ipPortRemoteInfos.getPort(ctx);
        String key = ip + ":" + port.intValue();
        for (String address : TransactionContainorBean.servers) {
            if (address.equals(key)) {
                int i = 0;
                for (String losed : TransactionContainorBean.losedServer) {
                    if (losed.equals(key)) {
                        TransactionContainorBean.losedServer.remove(i);
                        //通知连接对应的服务器
                        SimpleConnectionNettyClient connectionNettyClient = new SimpleConnectionNettyClient(receiptDistributionAbs);
                        connectionNettyClient.connection(key);
                        return;
                    }
                    i++;
                }
            }
        }
    }

    /**
     * 读取客户端传递过来的数据并且进行处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TransactionDefinationEntity) {
            TransactionDefinationEntity entity = (TransactionDefinationEntity) msg;
            int mode = (int) (entity.getId() % TransactionContainorBean.taskExecutorsEntities.size());
            TaskExecutorsEntity executorsEntity = TransactionContainorBean.taskExecutorsEntities.get(mode);
            executorsEntity.addDefinationEntity(ctx, entity);
        }
//        receiptDistributionAbs.receiptDataAndDeal(ctx, msg);
    }

    /**
     * 发送异常的处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发送获取接收异常");
//        super.exceptionCaught(ctx, cause);
    }
}
