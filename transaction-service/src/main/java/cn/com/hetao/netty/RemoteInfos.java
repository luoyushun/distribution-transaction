package cn.com.hetao.netty;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 14:55
 *@desc
 **/
public interface RemoteInfos {

    public <T> String getIp(T t);

    public <T> Integer getPort(T t);

    public <R,P> R getOther(P p);
}
