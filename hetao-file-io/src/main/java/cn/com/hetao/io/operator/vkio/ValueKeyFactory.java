package cn.com.hetao.io.operator.vkio;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 18:30
 *@desc 这个是键与值的工厂类
 **/
public interface ValueKeyFactory {

    /**
     * 设置值和键
     * @param key
     * @param datas
     * @param <T>
     * @return
     */
    public <T> boolean setValue(String key, T datas) throws Exception;

    /**
     * 设置值和键已经超时时间
     * @param key
     * @param datas
     * @param timeout
     * @param <T>
     * @return
     */
    public <T> boolean setValue(String key, T datas, long timeout) throws Exception;

    /**
     * 设置值当数据中没有的时候
     * @param key
     * @param datas
     * @param <T>
     * @return
     */
    public <T> boolean setValueNE(String key, T datas) throws Exception;

    /**
     * 设置值当值不存在的时候，同时设置超时时间
     * @param key
     * @param datas
     * @param timeout
     * @param <T>
     * @return
     */
    public <T> boolean setValueNE(String key, T datas, long timeout) throws Exception;

    /**
     * 更新值，当值存在的时候
     * @param key
     * @param datas
     * @param <T>
     * @return
     */
    public <T> boolean setValueE(String key, T datas) throws Exception;

    /**
     * 更新值，当值存在的时候，同时设置超时时间
     * @param key
     * @param datas
     * @param timeout
     * @param <T>
     * @return
     */
    public <T> boolean setValueE(String key, T datas, long timeout) throws Exception;

    /**
     * 设置键的超时时间
     * @param key
     * @param timeout
     * @param <T>
     * @return
     */
    public <T> boolean setKeyTimeout(String key, long timeout) throws Exception;

    /**
     * 获取键的值
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getValue(String key, T data) throws Exception;

    /**
     * 获取值的同时设置值
     * @param key
     * @param datas
     * @param <T>
     * @return
     */
    public <T> T getAndSetValue(String key, T datas) throws Exception;

    /**
     * 获取值的同时设置值和超时时间
     * @param key
     * @param datas
     * @param timeout
     * @param <T>
     * @return
     */
    public <T> T getAndSetValue(String key, T datas, long timeout) throws Exception;

}
