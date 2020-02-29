package cn.com.hetao.io.operator.vkio;

import cn.com.hetao.io.config.KeyObjectDefination;

import java.util.List;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/27 13:56
 *@desc 这个是刷盘机制的类
 **/
public interface RefreshDiskFactory {

    /**
     * 这个是通过键来进行刷新数据
     * @param keys
     * @return
     */
    public boolean refreshSaveDiskValueDatas(List<KeyObjectDefination> keys) throws Exception;

    /**
     * 刷新键
     * @param keys
     * @return
     */
    public boolean refreshSaveDiskKeyDatas(List<KeyObjectDefination> keys) throws Exception;

}
