package cn.com.hetao.io.operator.vkio;

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
    public List<Map<String, Object>> refreshValue(List<Map<String, Object>> keys) throws Exception;

    /**
     * 刷新保存数据
     * @param keys
     * @return
     */
    public boolean refreshKeys(List<Map<String, Object>> keys) throws Exception;

}
