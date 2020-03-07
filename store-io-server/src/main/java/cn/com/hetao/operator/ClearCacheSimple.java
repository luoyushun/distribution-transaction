package cn.com.hetao.operator;

import cn.com.hetao.config.StoreBean;
import cn.com.hetao.entity.ValueObjectDefination;
import cn.com.hetao.io.config.KeyObjectDefination;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/5 19:23
 *@desc
 **/
public class ClearCacheSimple implements ClearCache {

    @Override
    public void clearCache() {
        synchronized (StoreBean.valueBeanList) {
            // 这里进行循环来查询数据信息
            int size = StoreBean.valueBeanList.size();
            for (int i  = 0; i< size; i++) {
                ValueObjectDefination defination = StoreBean.valueBeanList.get(i);
                // 这个是查询是否含有超时的数据，如果有，那么进行操作
                if (defination.getTimes() >= System.currentTimeMillis()) {
                    StoreBean.keyFactory.deleteKeyInfo(defination.getKey());
                    StoreBean.keyBeans.remove(defination.getKey());
                    StoreBean.valueBeanList.remove(i);
                    StoreBean.valueBeans.remove(defination.getKey());
                    i--;
                    size = StoreBean.valueBeanList.size();
                }
                // 这个是查询是否有删除的
                KeyObjectDefination keyObjectDefination = StoreBean.keyBeans.get(defination.getKey());
                if (keyObjectDefination == null) continue;
                if (keyObjectDefination.isDelete()) {
                    StoreBean.keyFactory.deleteKeyInfo(defination.getKey());
                    StoreBean.keyBeans.remove(defination.getKey());
                    StoreBean.valueBeanList.remove(i);
                    StoreBean.valueBeans.remove(defination.getKey());
                    i--;
                    size = StoreBean.valueBeanList.size();
                }
            }
        }
    }
}
