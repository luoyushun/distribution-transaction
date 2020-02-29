package cn.com.hetao.io.operator.vkio;

import cn.com.hetao.io.config.KeyObjectDefination;

import java.util.List;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/27 14:12
 *@desc 对数据进行保存和刷新
 **/
public interface KVRefreshDiskFactory {

    /**
     * 刷新保存数据
     * @param keys
     * @return
     */
    public List<KeyObjectDefination> refreshValue(List<KeyObjectDefination> keys) throws Exception;

    /**
     * 刷新保存数据
     * @param keys
     * @return
     */
    public boolean refreshKeys(List<KeyObjectDefination> keys) throws Exception;

}
