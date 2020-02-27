package cn.com.hetao.io.operator.vkio;

import java.util.List;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 18:48
 *@desc 这个是键的工厂类
 **/
public interface KeyFactory {

    static final String point = "point";
    static final String length = "length";
    static final String name = "name";
    static final String time = "time";

    /**
     * 获取单个键的信息
     * @param key
     * @return
     */
    public Map<String, Object> getKeyInfo(String key) throws Exception;

    /**
     * 获取目前没有过期的键
     * @return
     */
    public List<Map<String, Object>> getKeysInfo() throws Exception;

    /**
     * 添加键的信息
     * @param keyInfo
     * @return
     */
    public boolean addKeyInfo(Map<String, Object> keyInfo) throws Exception;

    /**
     * 删除键
     * @param key
     * @return
     */
    public boolean deleteKeyInfo(String key);

}
