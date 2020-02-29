package cn.com.hetao.io.operator.vkio.impl;

import cn.com.hetao.io.config.FileOperatorConfig;
import cn.com.hetao.io.config.KeyObjectDefination;
import cn.com.hetao.io.operator.vkio.KeyFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 19:43
 *@desc
 **/
public class KeyAdaptor implements KeyFactory {

    protected String defaultFileName = "default_k_v.idx";

    protected KeyObjectDefination getKeyString(String key, RandomAccessFile accessFile) throws Exception {
        List<Byte> bytes = new ArrayList<Byte>();
        while (true) {
            byte b = - 1;
            try {
                b = accessFile.readByte();
                if (b == 10) {
                    if (bytes.isEmpty()) continue;
                    byte[] bt = new byte[bytes.size()];
                    for (int i=0; i < bt.length; i++) {
                        bt[i] = bytes.get(i);
                    }
                    String str = new String(bt);
                    String[] strs = str.split(":");
                    if (strs[0].equals(key)) {
                        KeyObjectDefination defination = new KeyObjectDefination();
                        defination.setName(strs[0]);
                        defination.setTimeout(Long.parseLong(strs[1]));
                        defination.setPoint(Long.parseLong(strs[2]));
                        defination.setLength(Long.parseLong(strs[3]));
                        return defination;
                    }
                    bytes = new ArrayList<Byte>();
                } else {
                    bytes.add(b);
                }
            } catch (Exception e) {
                b = -1;
            }
            if (b == -1) break;
            if (accessFile.getFilePointer() >= accessFile.length()) break;
        }
        return null;
    }

    @Override
    public KeyObjectDefination getKeyInfo(String key) throws Exception {
        String dataPath = FileOperatorConfig.index + File.separator + defaultFileName;
        File file = new File(dataPath);
        if (!file.exists()) return null;
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(dataPath, "r");
            accessFile.seek(0);
            KeyObjectDefination datas = getKeyString(key, accessFile);
            return datas;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (accessFile != null) {
                accessFile.close();
            }
        }
        return null;
    }

    @Override
    public List<KeyObjectDefination> getKeysInfo() throws Exception {
        String dataPath = FileOperatorConfig.index + File.separator + defaultFileName;
        File file = new File(dataPath + File.separator + defaultFileName);
        if (!file.exists()) return null;
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(dataPath, "r");
            accessFile.seek(0);
            List<Byte> bytes = new ArrayList<Byte>();
            List<KeyObjectDefination> keys = new ArrayList<KeyObjectDefination>();
            while (true) {
                byte b = -1;
                try {
                    b = accessFile.readByte();
                    if (b == 10) {
                        if (bytes.isEmpty()) continue;
                        byte[] bt = new byte[bytes.size()];
                        for (int i = 0; i < bt.length; i++) {
                            bt[i] = bytes.get(i);
                        }
                        String str = new String(bt);
                        String[] strs = str.split(":");
                        if (strs.length != 4) {
                            bytes = new ArrayList<Byte>();
                            continue;
                        }
                        KeyObjectDefination defination = new KeyObjectDefination();
                        defination.setName(strs[0]);
                        defination.setTimeout(Long.parseLong(strs[1]));
                        defination.setPoint(Long.parseLong(strs[2]));
                        defination.setLength(Long.parseLong(strs[3]));
                        keys.add(defination);
                        bytes = new ArrayList<Byte>();
                    } else {
                        bytes.add(b);
                    }
                } catch (Exception e) {
                    b = -1;
                }
                if (b == -1) break;
                if (accessFile.getFilePointer() >= accessFile.length()) break;
            }
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (accessFile != null) {
                accessFile.close();
            }
        }
        return null;
    }

    @Override
    public boolean addKeyInfo(KeyObjectDefination keyObjectDefination) throws Exception {
        String dataPath = FileOperatorConfig.index;
        File file = new File(dataPath);
        if (!file.exists()) file.mkdirs();
        file = new File(file, defaultFileName);
        if (!file.exists()) file.createNewFile();
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(file, "rws");
            accessFile.seek(accessFile.length());
            String str = keyObjectDefination.getName() + ":"
                    + keyObjectDefination.getTimeout().longValue() + ":"
                    + keyObjectDefination.getPoint().longValue() + ":"
                    + keyObjectDefination.getLength().longValue();
            str = str + "\n";
            accessFile.write(str.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try{
                if (accessFile != null ) {
                    accessFile.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean deleteKeyInfo(String key) {
        String dataPath = FileOperatorConfig.index + File.separator + defaultFileName;
        String tempPath = FileOperatorConfig.dataTempPath + File.separator + "default_k_v.temp";
        File file = new File(dataPath + File.separator + defaultFileName);
        File fileTemp = new File(FileOperatorConfig.dataTempPath);
        if (!file.exists()) return false;
        if (!fileTemp.exists()) fileTemp.mkdirs();
        RandomAccessFile accessFile = null;
        RandomAccessFile accessFileTemp = null;
        try {
            accessFile = new RandomAccessFile(dataPath, "r");
            accessFileTemp = new RandomAccessFile(tempPath + File.separator + "default_k_v.temp", "rws");
            accessFile.seek(0);
            accessFileTemp.seek(0);
            List<Byte> bytes = new ArrayList<Byte>();
            while (true) {
                byte b = - 1;
                try {
                    b = accessFile.readByte();
                    if (b == 10) {
                        if (bytes.isEmpty()) continue;
                        byte[] bt = new byte[bytes.size()];
                        for (int i=0; i < bt.length; i++) {
                            bt[i] = bytes.get(i);
                        }
                        String str = new String(bt);
                        String[] strs = str.split(":");
                        if (strs[0].equals(key)) continue;
                        bytes = new ArrayList<Byte>();
                        if (accessFileTemp.length() == 0) {
                            accessFileTemp.write(str.getBytes());
                        } else {
                            str = "\n" + str;
                            accessFileTemp.write(str.getBytes());
                        }
                    } else {
                        bytes.add(b);
                    }
                } catch (Exception e) {
                    b = -1;
                }
                if (b == -1) break;
                if (accessFile.getFilePointer() >= accessFile.length()) break;
            }
            accessFile.close();
            file.delete();
            accessFile = new RandomAccessFile(dataPath, "rws");
            byte[] bt = new byte[1024];
            int length = -1;
            accessFileTemp.seek(0);
            while ((length = accessFileTemp.read(bt)) != -1) {
                accessFile.write(bt, 0, length);
            }
            fileTemp.delete();
            accessFileTemp.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (accessFile != null) {
                try {
                    accessFile.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (accessFileTemp != null) {
                try {
                    accessFileTemp.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
