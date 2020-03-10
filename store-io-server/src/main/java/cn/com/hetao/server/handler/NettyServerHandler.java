package cn.com.hetao.server.handler;

import cn.com.hetao.config.StoreBean;
import cn.com.hetao.entity.SimpleIpPortRemoteInfos;
import cn.com.hetao.server.entity.NettyClientBean;
import cn.com.hetao.server.entity.NoticeEntity;
import cn.com.hetao.server.impl.NettyClinetSimple;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.catalina.Store;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/8 19:33
 *@desc 这个是处理数据的
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private DealNewDataHandlerSimple simple;

    private NettyClinetSimple nettyClient;

    public NettyServerHandler(DealNewDataHandlerSimple simple, NettyClinetSimple nettyClient) {
        this.simple = simple;
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       // 这个是来处理数据的，处理数据的
        if (msg instanceof NoticeEntity) {
            NettyServerDataDeal dataDeal = new NettyServerDataDealSimple(simple);
            dataDeal.dataDeal(ctx, (NoticeEntity) msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        SimpleIpPortRemoteInfos ipPortRemoteInfos = new SimpleIpPortRemoteInfos();
        String ip = ipPortRemoteInfos.getIp(ctx);
        Integer port = ipPortRemoteInfos.getPort(ctx);
        String url = ip + ":" + port.intValue();
        NettyClientBean netty = nettyClient.connectNettyClientOne(url);
        for (int i = 0; i< StoreBean.lostNettyClient.size(); i++) {
            String u = StoreBean.lostNettyClient.get(i);
            if (u.equals(url)) {
                StoreBean.lostNettyClient.remove(i);
                break;
            }
        }
        StoreBean.nettyClientBeans.add(netty);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        SimpleIpPortRemoteInfos ipPortRemoteInfos = new SimpleIpPortRemoteInfos();
        String ip = ipPortRemoteInfos.getIp(ctx);
        Integer port = ipPortRemoteInfos.getPort(ctx);
        String url = ip + ":" + port.intValue();
        for (int i = 0; i < StoreBean.nettyClientBeans.size(); i++) {
            NettyClientBean nettyClientBean = StoreBean.nettyClientBeans.get(i);
            if (nettyClientBean.getUrl().equals(url)) {
                StoreBean.nettyClientBeans.remove(i);
                StoreBean.lostNettyClient.add(url);
                break;
            }
        }
    }
}
