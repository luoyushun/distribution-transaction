package cn.com.hetao.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 14:57
 *@desc 获取客户端信息
 **/
public class SimpleIpPortRemoteInfos implements RemoteInfos {

    private InetSocketAddress getInetAddress(Object o) {
        ChannelHandlerContext chx = null;
        if ( o instanceof ChannelHandlerContext) {
            chx = (ChannelHandlerContext) o;
        }
        if (chx == null) return null;
        Channel channel = chx.channel();
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        return socketAddress;
    }

    /**
     * 获取客户端的Ip地址
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public <T> String getIp(T t) {
        InetSocketAddress socketAddress = getInetAddress(t);
        if (socketAddress == null) return null;
        InetAddress address = socketAddress.getAddress();
        if (address == null) return null;
        String ip = address.getHostAddress();
        return ip;
    }

    /**
     * 获取客户端的端口号
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public <T> Integer getPort(T t) {
        InetSocketAddress socketAddress = getInetAddress(t);
        if (socketAddress == null) return 0;
        Integer port = socketAddress.getPort();
        return port;
    }

    @Override
    public <R, P> R getOther(P p) {
        return null;
    }
}
