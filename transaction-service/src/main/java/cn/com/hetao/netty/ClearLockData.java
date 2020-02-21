package cn.com.hetao.netty;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 17:50
 *@desc 清理等待或者获取锁的容器通过ip和端口号
 **/
public interface ClearLockData {

    /**
     * 清理锁通过ip地址和端口号
     * @param ip
     * @param port
     */
    public void clearLockAndGainLock(String ip, Integer port);

}
