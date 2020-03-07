package cn.com.hetao.operator;

import cn.com.hetao.config.StoreBean;
import cn.com.hetao.entity.ValueObjectDefination;
import cn.com.hetao.entity.ValueObjectSort;
import cn.com.hetao.io.config.KeyObjectDefination;

import java.util.Collections;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/5 19:35
 *@desc 这个是将最低访问进行存储到文件中
 **/
public class ClearCacheUpdate implements ClearCache {

    @Override
    public void clearCache() {
        synchronized (StoreBean.valueBeanList) {
            Collections.sort(StoreBean.valueBeanList, new ValueObjectSort());
            ValueObjectDefination defination = StoreBean.valueBeanList.get(0);
            KeyObjectDefination keyObjectDefination = StoreBean.keyBeans.get(defination.getKey());
            if (!keyObjectDefination.isDisk()) {
                try {
                    KeyObjectDefination kd = StoreBean.valueFactory.addValue(defination.getValue());
                    keyObjectDefination.setPoint(kd.getPoint());
                    keyObjectDefination.setLength(kd.getLength());
                    StoreBean.keyFactory.deleteKeyInfo(kd.getName());
                    StoreBean.keyFactory.addKeyInfo(keyObjectDefination);
                    StoreBean.valueBeanList.remove(0);
                    StoreBean.valueBeans.remove(defination.getKey());
                }catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                StoreBean.valueBeanList.remove(0);
                StoreBean.valueBeans.remove(defination.getKey());
            }
        }
    }

}
