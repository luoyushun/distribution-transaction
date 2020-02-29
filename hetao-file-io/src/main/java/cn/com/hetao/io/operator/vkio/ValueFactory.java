package cn.com.hetao.io.operator.vkio;

import cn.com.hetao.io.config.KeyObjectDefination;

import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 18:45
 *@desc 这个是值的工厂类
 **/
public interface ValueFactory {

    /**
     * 这个是获取对象的数据信息
     * @param start
     * @param length
     * @param <T>
     * @return
     */
    public <T> T getValue(long start, long length, T data) throws Exception;

    /**
     * 这个是添加值并且返回长度和位置信息
     * @param data
     * @param <T>
     * @return
     */
    public <T> KeyObjectDefination addValue(T data) throws Exception;

}
