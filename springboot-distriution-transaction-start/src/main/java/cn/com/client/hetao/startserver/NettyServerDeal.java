package cn.com.client.hetao.startserver;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 20:32
 *@desc
 **/
public interface NettyServerDeal {

    /**
     *
     * @param key 键值对
     * @param desc 描述
     *  不设置超时，表示永久性
     * @return true 表示获取资源， false 表示获取资源失败
     */
    public boolean lock(String key, String desc);

    /**
     *
     * @param key 键值对
     * @param desc 描述
     * @param timeout 设置超时时间 0表示永远等下去
     * @return true 表示获取资源， false 表示获取资源失败
     */
    public boolean lock(String key, String desc, Long timeout);

    /**
     *
     * @param key 键值对
     * @param desc 描述
     * @param priority 设置优先级，值越大，优先级越高
     * @return true 表示获取资源， false 表示获取资源失败
     */
    public boolean lock(String key, String desc, Integer priority);

    /**
     *
     * @param key 键值对
     * @param desc 描述
     * @param timeout  设置超时时间 0表示永远等下去
     * @param priority  设置优先级，值越大，优先级越高
     * @return  true 表示获取资源， false 表示获取资源失败
     */
    public boolean lock(String key, String desc, Long timeout, Integer priority);

    /**
     * 释放锁
     * @param key
     * @return
     */
    public boolean unLock(String key);

}
