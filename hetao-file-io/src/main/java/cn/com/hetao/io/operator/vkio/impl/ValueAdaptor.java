package cn.com.hetao.io.operator.vkio.impl;

import cn.com.hetao.io.config.FileOperatorConfig;
import cn.com.hetao.io.config.KeyObjectDefination;
import cn.com.hetao.io.operator.vkio.KeyFactory;
import cn.com.hetao.io.operator.vkio.ValueFactory;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 21:34
 *@desc
 **/
public class ValueAdaptor implements ValueFactory {

    protected String defaultData = "default_k_v_datas";

    protected <T> byte[] getByteToObject(T datas) throws IOException {
        ByteArrayOutputStream byt=new ByteArrayOutputStream();
        ObjectOutputStream obj=new ObjectOutputStream(byt);
        obj.writeObject(datas);
        byte[] bytes=byt.toByteArray();
        return bytes;
    }

    protected <T> T getObjectFromByte(byte[] datas, T condition) throws Exception {
        ByteArrayInputStream byteInt=new ByteArrayInputStream(datas);
        ObjectInputStream objInt=new ObjectInputStream(byteInt);
        Object obj = objInt.readObject();
        T t = (T) obj;
        return t;
    }

    @Override
    public <T> T getValue(long start, long length, T data) throws Exception {
        String datasPath = FileOperatorConfig.dataPath + File.separator + defaultData + File.separator + defaultData + ".data";
        File file = new File(FileOperatorConfig.dataPath + File.separator + defaultData);
        if (!file.exists()) return null;
        file = new File(file, defaultData + ".data");
        if (!file.exists()) return null;
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(datasPath, "r");
            randomFile.seek(start);
            byte[] bytes = new byte[(int) length];
            int len = randomFile.read(bytes);
            if (len < 0) return null;
            if (len < length) return null;
            T t = getObjectFromByte(bytes, data);
            return t;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (randomFile != null) {
                try{
                    randomFile.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public <T> KeyObjectDefination addValue(T data) throws Exception {
        if (data == null) return null;
        KeyObjectDefination datas = new KeyObjectDefination();
        String datasPath = FileOperatorConfig.dataPath + File.separator + defaultData + File.separator + defaultData + ".data";
        File file = new File(FileOperatorConfig.dataPath + File.separator + defaultData);
        if (!file.exists()) file.mkdirs();
        file = new File(file, defaultData + ".data");
        if (!file.exists()) file.createNewFile();
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(datasPath, "rws");
            randomFile.seek(randomFile.length());
            byte[] bytes = getByteToObject(data);
            int len = bytes.length;
            long point = randomFile.getFilePointer();
            randomFile.write(bytes);
            datas.setPoint(point);
            datas.setLength((long) len);
            return datas;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (randomFile != null) {
                try{
                    randomFile.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
