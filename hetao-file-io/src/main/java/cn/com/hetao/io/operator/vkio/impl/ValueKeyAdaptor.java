package cn.com.hetao.io.operator.vkio.impl;

import cn.com.hetao.io.config.KeyObjectDefination;
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
        KeyObjectDefination defination = new KeyObjectDefination();
        KeyObjectDefination tempData = valueFactory.addValue(datas);
        if (tempData == null) return false;
        defination.setTimeout(timeout);
        defination.setName(key);
        defination.setPoint(tempData.getPoint());
        defination.setLength(tempData.getLength());
        KeyObjectDefination obj = keyFactory.getKeyInfo(key);
        if (obj == null) {
            return keyFactory.addKeyInfo(defination);
        } else {
            boolean c = keyFactory.deleteKeyInfo(key);
            if (c) {
               return keyFactory.addKeyInfo(defination);
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
        KeyObjectDefination obj = keyFactory.getKeyInfo(key);
        Long time = obj.getTimeout();
        if (obj != null && (time > Calendar.getInstance().getTimeInMillis() || (time == 0))) return false;
        KeyObjectDefination tempData = valueFactory.addValue(datas);
        if (tempData == null) return false;
        KeyObjectDefination data = new KeyObjectDefination();
        data.setTimeout(timeout);
        data.setName(key);
        data.setPoint(tempData.getPoint());
        data.setLength(tempData.getLength());
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
        KeyObjectDefination obj = keyFactory.getKeyInfo(key);
        Long time = obj.getTimeout();
        if (obj == null && (time <= Calendar.getInstance().getTimeInMillis() && (time != 0))) return false;
        return setValue(key, datas, timeout);
    }

    @Override
    public <T> boolean setKeyTimeout(String key, long timeout) throws Exception {
        KeyObjectDefination obj = keyFactory.getKeyInfo(key);
        if (obj == null) return false;
        obj.setTimeout(timeout);
        boolean c = keyFactory.deleteKeyInfo(key);
        if (c) {
            return keyFactory.addKeyInfo(obj);
        }
        return false;
    }

    @Override
    public <T> T getValue(String key, T data) throws Exception {
        KeyObjectDefination obj = keyFactory.getKeyInfo(key);
        if (obj == null) return null;
        Long time = obj.getTimeout();
        if ((time <= Calendar.getInstance().getTimeInMillis() && (time != 0))) return null;
        return valueFactory.getValue(obj.getPoint(), obj.getLength(), data);
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
