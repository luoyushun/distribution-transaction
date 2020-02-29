package cn.com.hetao.io.operator.vkio;

import cn.com.hetao.io.config.KeyObjectDefination;

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
    public KeyObjectDefination getKeyInfo(String key) throws Exception;

    /**
     * 获取目前没有过期的键
     * @return
     */
    public List<KeyObjectDefination> getKeysInfo() throws Exception;

    /**
     * 添加键的信息
     * @param keyObjectDefination
     * @return
     */
    public boolean addKeyInfo(KeyObjectDefination keyObjectDefination) throws Exception;

    /**
     * 删除键
     * @param key
     * @return
     */
    public boolean deleteKeyInfo(String key);

}
