package cn.com.hetao.io.operator.vkio.impl;

import cn.com.hetao.io.config.FileOperatorConfig;
import cn.com.hetao.io.config.KeyObjectDefination;
import cn.com.hetao.io.operator.vkio.KVRefreshDiskFactory;
import cn.com.hetao.io.operator.vkio.KeyFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/27 14:15
 *@desc 这个是保存数据的
 **/
public class KVRefreshDiskAdaptor implements KVRefreshDiskFactory {

    protected String defaultFileName = "default_k_v.idx";
    protected String defaultData = "default_k_v_datas";

    @Override
    public List<KeyObjectDefination> refreshValue(List<KeyObjectDefination> keys) throws Exception {
        String datasPath = FileOperatorConfig.dataPath + File.separator + defaultData + File.separator + defaultData + ".data";
        String dateTempPath = FileOperatorConfig.dataTempPath + File.separator + defaultData + File.separator + defaultData + ".temp";
        File file = new File(FileOperatorConfig.dataPath + File.separator + defaultData);
        if (!file.exists()) return null;
        file = new File(file, defaultData + ".data");
        if (!file.exists()) return null;
        file = new File(FileOperatorConfig.dataTempPath + File.separator + defaultData);
        if (!file.exists()) file.mkdirs();
        file = new File(file, defaultData + ".temp");
        if (file.exists()) file.delete();
        file = new File(file, defaultData + ".temp");
        file.createNewFile();
        RandomAccessFile randomFile = null;
        RandomAccessFile randomTempFile = null;
        List<KeyObjectDefination> result = new ArrayList<>();
        try {
            randomFile = new RandomAccessFile(datasPath, "r");
            randomTempFile = new RandomAccessFile(dateTempPath, "rws");
            randomTempFile.seek(0);
            for (KeyObjectDefination data : keys) {
                Long point = data.getPoint();
                Long length = data.getLength();
                byte[] bytes = new byte[Math.toIntExact(length)];
                randomFile.seek(point);
                int len = randomFile.read(bytes);
                if (len == -1) continue;
                if (len != length) continue;
                long pot = randomTempFile.getFilePointer();
                randomTempFile.write(bytes);
                data.setPoint(pot);
                result.add(data);
                if (randomFile.getFilePointer() >= randomFile.length()) break;
            }
            file = new File(datasPath);
            file.delete();
            file = new File(datasPath);
            file.createNewFile();
            randomFile.close();
            randomFile = new RandomAccessFile(datasPath, "rws");
            randomFile.seek(0);
            randomTempFile.seek(0);
            byte[] dt = new byte[1024];
            int len = -1;
            while ((len = randomTempFile.read(dt)) != -1) {
                randomFile.write(dt, 0, len);
            }
            file = new File(dateTempPath);
            file.delete();
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (randomFile != null) randomFile.close();;
            if (randomTempFile != null) randomTempFile.close();
        }
        return result;
    }

    @Override
    public boolean refreshKeys(List<KeyObjectDefination> keys) throws Exception {
        if (keys == null || keys.isEmpty()) return true;
        String dataPath = FileOperatorConfig.index + File.separator + defaultFileName;
        String dataTempPath = FileOperatorConfig.index + File.separator + defaultFileName + ".bak";
        File file = new File(dataPath);
        if (!file.exists()) return false;
        file.renameTo(new File(dataTempPath));
        file = new File(dataPath);
        file.delete();
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(dataPath, "rws");
            for (KeyObjectDefination keyInfo : keys) {
                String str = keyInfo.getName() + ":"
                        + keyInfo.getTimeout().longValue() + ":"
                        + keyInfo.getPoint().longValue() + ":"
                        + keyInfo.getLength().longValue();
                str = str + "\n";
                accessFile.write(str.getBytes());
            }
            file = new File(dataTempPath);
            file.delete();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (accessFile != null) accessFile.close();
        }
        return false;
    }
}
