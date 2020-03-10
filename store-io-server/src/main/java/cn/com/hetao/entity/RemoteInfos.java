package cn.com.hetao.entity;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 14:55
 *@desc 获取客户端的信息接口
 **/
public interface RemoteInfos {

    public <T> String getIp(T t);

    public <T> Integer getPort(T t);

    public <R,P> R getOther(P p);
}
