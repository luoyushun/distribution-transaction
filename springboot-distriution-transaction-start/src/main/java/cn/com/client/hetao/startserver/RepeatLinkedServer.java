package cn.com.client.hetao.startserver;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 19:10
 *@desc
 **/
public interface RepeatLinkedServer {

    /**
     * 如果服务不能用了，可以连接这个来进行进行尝试连接服务
     */
    public void repeatLinkedServer();

    /**
     * 服务从新连接时，如何处理未处理的lock问题
     * @param status
     */
    public void dealLockServer(Integer status);

}
