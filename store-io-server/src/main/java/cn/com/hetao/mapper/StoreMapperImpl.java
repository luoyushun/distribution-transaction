package cn.com.hetao.mapper;

import cn.com.hetao.config.StoreBean;
import cn.com.hetao.entity.ValueObjectDefination;
import cn.com.hetao.io.config.KeyObjectDefination;
import cn.com.hetao.operator.ClearCache;
import cn.com.hetao.operator.ClearCacheSimple;
import cn.com.hetao.operator.ClearCacheUpdate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/29 16:01
 *@desc 这个是保存数据或者设置数据,这不能提供本身直接使用
 **/
@Component
public class StoreMapperImpl implements StoreMapper {

    protected <T> byte[] getByteToObject(T datas) throws IOException {
        ByteArrayOutputStream byt=new ByteArrayOutputStream();
        ObjectOutputStream obj=new ObjectOutputStream(byt);
        obj.writeObject(datas);
        byte[] bytes=byt.toByteArray();
        return bytes;
    }

    @Override
    public Object getValue(String key) {
        try {
            KeyObjectDefination defination = StoreBean.keyBeans.get(key);
            if (defination == null || defination.isDelete()) return null;
            if (defination.getTimeout() < Calendar.getInstance().getTimeInMillis()) {
                defination.setDisk(false);
                defination.setDelete(true);
                return null;
            }
            ValueObjectDefination valueObjectDefination = StoreBean.valueBeans.get(key);
            if (valueObjectDefination == null) {
                Object value = StoreBean.valueFactory.getValue(defination.getPoint(), defination.getLength(), new Object());
                valueObjectDefination = new ValueObjectDefination();
                valueObjectDefination.setCreateTime(new Date());
                valueObjectDefination.setLastAccessTime(new Date());
                valueObjectDefination.setKey(key);
                valueObjectDefination.setTimes(1L);
                valueObjectDefination.setValue(value);
                StoreBean.valueBeans.put(key, valueObjectDefination);
                byte[] result = getByteToObject(StoreBean.valueBeanList);
                ClearCache clearCache = null;
                if (result.length >= StoreBean.cacheSize) {
                    // 这里要进行清查缓存
                    clearCache = new ClearCacheSimple();
                    clearCache.clearCache();
                }
                if (result.length >= StoreBean.cacheSize) {
                    // 这里进行将最近没有访问的进行保存到文件中
                    clearCache = new ClearCacheUpdate();
                    clearCache.clearCache();
                }
                StoreBean.valueBeanList.add(valueObjectDefination);
            } else {
                valueObjectDefination.setLastAccessTime(new Date());
                valueObjectDefination.setTimes(valueObjectDefination.getTimes() + 1);
            }
            return valueObjectDefination.getValue();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setTimeout(String key, Long timeout) {
        KeyObjectDefination defination = StoreBean.keyBeans.get(key);
        if (defination == null) return;
        defination.setTimeout(timeout);
        defination.setDisk(false);
        defination.setDelete(false);
    }

    @Override
    public boolean setValue(String key, Object value, Long timeout) {
        try {
            boolean c = StoreBean.valueKey.setValue(key, value, timeout);
            if (c) {
                KeyObjectDefination defination = StoreBean.keyFactory.getKeyInfo(key);
                if (defination == null) return false;
                KeyObjectDefination keyDefination = StoreBean.keyBeans.get(key);
                if (keyDefination == null) {
                    StoreBean.keyBeans.put(key, defination);
                } else {
                    keyDefination.setLength(defination.getLength());
                    keyDefination.setPoint(defination.getPoint());
                    keyDefination.setTimeout(timeout);
                }
            }
            return c;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean setNXValue(String key, Object value, Long timeout, Comparator comparator) {
        KeyObjectDefination keyObjectDefination = StoreBean.keyBeans.get(key);
        if (keyObjectDefination != null
                && (keyObjectDefination.getTimeout() > System.currentTimeMillis()
                && keyObjectDefination.getTimeout() != 0
                || keyObjectDefination.getTimeout() == 0)
                && !keyObjectDefination.isDelete()) {
            // 这里要进行判断
            ValueObjectDefination defination = StoreBean.valueBeans.get(key);
            if (comparator.compare(value, defination.getValue()) > 0) {
                // 这里进行删除数据
                StoreBean.valueBeans.remove(key);
                StoreBean.keyBeans.remove(key);
            } else {
                return false;
            }
        }
        return setValue(key, value, timeout);
    }

    @Override
    public boolean setEXValue(String key, Object value, Long timeout, Comparator comparator) {
        KeyObjectDefination keyObjectDefination = StoreBean.keyBeans.get(key);
        if (keyObjectDefination == null
                || (keyObjectDefination.getTimeout() <= System.currentTimeMillis()
                && keyObjectDefination.getTimeout() != 0 )
                || keyObjectDefination.getTimeout() == 0
                || keyObjectDefination.isDelete()) {
            return false;
        }
        return setValue(key, value, timeout);
    }

    @Override
    public boolean deleteValue(String key) {
        boolean c = StoreBean.keyFactory.deleteKeyInfo(key);
        if (c) {
            StoreBean.keyBeans.remove(key);
            StoreBean.valueBeans.remove(key);
            int size = StoreBean.valueBeanList.size();
            for (int i = 0; i < size; i++) {
                ValueObjectDefination defination = StoreBean.valueBeanList.get(i);
                if (defination.getKey().equals(key)) {
                    StoreBean.valueBeanList.remove(i);
                    break;
                }
            }
        }
        return c;
    }
}
