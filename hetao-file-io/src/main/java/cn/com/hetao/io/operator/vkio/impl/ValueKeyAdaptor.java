package cn.com.hetao.io.operator.vkio.impl;

import cn.com.hetao.io.operator.vkio.KeyFactory;
import cn.com.hetao.io.operator.vkio.ValueFactory;
import cn.com.hetao.io.operator.vkio.ValueKeyFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/27 11:27
 *@desc 这个是对键与值的操作
 **/
public class ValueKeyAdaptor implements ValueKeyFactory {

    protected KeyFactory keyFactory = new KeyAdaptor();
    protected ValueFactory valueFactory = new ValueAdaptor();

    @Override
    public <T> boolean setValue(String key, T datas) throws Exception {

        return setValue(key, datas, 0);
    }

    @Override
    public <T> boolean setValue(String key, T datas, long timeout) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Long> tempData = valueFactory.addValue(datas);
        if (tempData == null) return false;
        data.put(KeyFactory.time, timeout);
        data.put(KeyFactory.name, key);
        data.put(KeyFactory.point, tempData.get(KeyFactory.point));
        data.put(KeyFactory.length, tempData.get(KeyFactory.length));
        Map<String, Object> obj = keyFactory.getKeyInfo(key);
        if (obj == null) {
            return keyFactory.addKeyInfo(data);
        } else {
            boolean c = keyFactory.deleteKeyInfo(key);
            if (c) {
               return keyFactory.addKeyInfo(data);
            } else {
                return false;
            }
        }
    }

    @Override
    public <T> boolean setValueNE(String key, T datas) throws Exception {

        return setValueNE(key, datas, 0);
    }

    @Override
    public <T> boolean setValueNE(String key, T datas, long timeout) throws Exception {
        Map<String, Object> obj = keyFactory.getKeyInfo(key);
        Long time = (Long) obj.get(KeyFactory.time);
        if (obj != null && (time > Calendar.getInstance().getTimeInMillis() || (time == 0))) return false;
        Map<String, Long> tempData = valueFactory.addValue(datas);
        if (tempData == null) return false;
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(KeyFactory.time, timeout);
        data.put(KeyFactory.name, key);
        data.put(KeyFactory.point, tempData.get(KeyFactory.point));
        data.put(KeyFactory.length, tempData.get(KeyFactory.length));
        if (obj == null) {
           return keyFactory.addKeyInfo(data);
        } else {
           boolean c =  keyFactory.deleteKeyInfo(key);
           if (c) {
               return keyFactory.addKeyInfo(data);
           } else {
               return false;
           }
        }
    }

    @Override
    public <T> boolean setValueE(String key, T datas) throws Exception {
        return setValueE(key, datas, 0);
    }

    @Override
    public <T> boolean setValueE(String key, T datas, long timeout) throws Exception {
        Map<String, Object> obj = keyFactory.getKeyInfo(key);
        Long time = (Long) obj.get(KeyFactory.time);
        if (obj == null && (time <= Calendar.getInstance().getTimeInMillis() && (time != 0))) return false;
        return setValue(key, datas, timeout);
    }

    @Override
    public <T> boolean setKeyTimeout(String key, long timeout) throws Exception {
        Map<String, Object> obj = keyFactory.getKeyInfo(key);
        if (obj == null) return false;
        obj.put(KeyFactory.time, timeout);
        boolean c = keyFactory.deleteKeyInfo(key);
        if (c) {
            return keyFactory.addKeyInfo(obj);
        }
        return false;
    }

    @Override
    public <T> T getValue(String key, T data) throws Exception {
        Map<String, Object> obj = keyFactory.getKeyInfo(key);
        if (obj == null) return null;
        Long time = (Long) obj.get(KeyFactory.time);
        if ((time <= Calendar.getInstance().getTimeInMillis() && (time != 0))) return null;
        return valueFactory.getValue((Long) obj.get(KeyFactory.point), (Long)obj.get(KeyFactory.length), data);
    }

    @Override
    public <T> T getAndSetValue(String key, T datas) throws Exception {
        return getAndSetValue(key, datas, 0);
    }

    @Override
    public <T> T getAndSetValue(String key, T datas, long timeout) throws Exception {
        T t = getValue(key, datas);
        boolean c = setValue(key, datas, timeout);
        if (c) {
            return t;
        } else {
            return null;
        }
    }
}
