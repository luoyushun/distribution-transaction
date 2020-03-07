package cn.com.hetao.mapper;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/29 15:58
 *@desc 这个是存储的映射文件
 **/
public interface StoreMapper {

    /**
     * 这个是获取数据
     * @param key
     * @return
     */
    public Object getValue(String key);

    /**
     * 这个是设置超时时间
     * @param key
     * @param timeout
     */
    public void setTimeout(String key, Long timeout);

    /**
     * 这个是设置值
     * @param key
     * @param value
     * @param timeout
     */
    public void setValue(String key, Object value, Long timeout);

}
