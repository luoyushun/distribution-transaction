package cn.com.hetao.io.operator.vkio.impl;

import cn.com.hetao.io.config.KeyObjectDefination;
import cn.com.hetao.io.operator.vkio.KVRefreshDiskFactory;
import cn.com.hetao.io.operator.vkio.RefreshDiskFactory;

import java.util.List;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/27 14:11
 *@desc 这个是保存数据的数据项
 **/
public class RefreshDiskAdaptor implements RefreshDiskFactory {

    protected KVRefreshDiskFactory kvRefreshDisk = new KVRefreshDiskAdaptor();

    @Override
    public boolean refreshSaveDiskValueDatas(List<KeyObjectDefination> keys) throws Exception {
        List<KeyObjectDefination> datas = kvRefreshDisk.refreshValue(keys);
        return refreshSaveDiskKeyDatas(datas);
    }

    @Override
    public boolean refreshSaveDiskKeyDatas(List<KeyObjectDefination> keys) throws Exception {
        return kvRefreshDisk.refreshKeys(keys);
    }
}
