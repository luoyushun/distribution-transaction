package cn.com.hetao.io.operator.simple;

import cn.com.hetao.io.config.FileOperatorConfig;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 14:19
 *@desc 这个是数据操作文档
 **/
public abstract class DataFileOperatorAbs implements DataFileOperator {

    protected <T> byte[] getByteToObject(T datas) throws IOException {
        ByteArrayOutputStream byt=new ByteArrayOutputStream();
        ObjectOutputStream obj=new ObjectOutputStream(byt);
        obj.writeObject(datas);
        byte[] bytes=byt.toByteArray();
        return bytes;
    }

    protected <T> T getObjectFromByte(byte[] datas, T condition, Comparator<T> comparator) throws Exception {
        ByteArrayInputStream byteInt=new ByteArrayInputStream(datas);
        ObjectInputStream objInt=new ObjectInputStream(byteInt);
        Object obj = objInt.readObject();
        T t = (T) obj;
        if (comparator.compare(t, condition) == 0) return t;
        return null;
    }

    protected byte[] decodeValue(ByteBuffer bytes) {
        int len = bytes.limit() - bytes.position();
        byte[] bytes1 = new byte[len];
        bytes.get(bytes1);
        return bytes1;
    }

    protected ByteBuffer encodeValue(byte[] value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(value.length);
        byteBuffer.clear();
        byteBuffer.get(value, 0, value.length);
        return byteBuffer;
    }

    protected int getLength(RandomAccessFile randomFile, boolean check) throws IOException {
        List<Byte> byteList = new ArrayList<Byte>();
        while (true) {
            byte b = -1;
            try {
                b = randomFile.readByte();
            } catch (Exception e) {
                return -1;
            }
            if (b == -1) break;
            //排除\n有两个的bug问题
            if (b == 10 && !check) {
                check = true;
                continue;
            }
            if (b == 10 && check) {
                check = false;
                break;
            }
            byteList.add(b);
        }
        if (byteList.isEmpty()) return -1;
        byte[] bytes = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            bytes[i] = byteList.get(i);
        }
        String length = new String(bytes);
        int len = Integer.parseInt(length);
        return len;
    }

    protected <T> RandomAccessFile operatorTempDatas(RandomAccessFile randomFile,String dataPath, T datas, String tag, Comparator<T> comparator) throws Exception {
        String datasTempPath = FileOperatorConfig.dataTempPath + File.separator + tag + ".temp";
        File file = new File(FileOperatorConfig.dataTempPath);
        if (!file.exists()) file.mkdirs();
        file = new File(file, tag + ".temp");
        if (!file.exists()) file.createNewFile();
        RandomAccessFile randomTempFile = new RandomAccessFile(datasTempPath, "rws");
        randomTempFile.seek(0);
        boolean check = true;
        // 查询数据所在的位置
        while (true) {
            int len = getLength(randomFile, check);
            if (len == -1) break;
            check = false;
            byte[] bts = new byte[len];
            randomFile.read(bts);
            T t1 = getObjectFromByte(bts, datas, comparator);
            if (t1 != null) continue;
            String str = "";
            if (randomTempFile.getFilePointer() == 0) {
                str = "" + len + "\n";
            } else {
                str = "\n" + len + "\n";
            }
            randomTempFile.write(str.getBytes());
            randomTempFile.write(bts);
            if (randomFile.getFilePointer() >= randomFile.length()) break;
        }
        randomFile.close();
        file = new File(dataPath);
        file.delete();
        randomFile = new RandomAccessFile(dataPath, "rws");
        byte[] b = new byte[1024];
        int length = 0;
        randomTempFile.seek(0);
        while ((length = randomTempFile.read(b)) != -1) {
            randomFile.write(b, 0, length);
        }
        randomTempFile.close();
        file = new File(datasTempPath);
        file.delete();
        return randomFile;
    }

    /**
     * 向文件的最后的位置添加数据
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     * @throws Exception
     */
    @Override
    public <T> boolean addData(T datas, String tag, Comparator<T> comparator) throws Exception {
        String datasPath = FileOperatorConfig.dataPath + File.separator + tag + File.separator + tag + ".data";
        File file = new File(FileOperatorConfig.dataPath + File.separator + tag);
        if (!file.exists()) file.mkdirs();
        file = new File(file, tag + ".data");
        if (!file.exists()) file.createNewFile();
        T temp = findOneDatas(datas, tag, comparator);
        if (temp != null) throw new Exception("数据已经存在，插入数据失败！");
        byte[] bytes=getByteToObject(datas);
        // 对象字节已经获取到了,接下来就是进行添加数据了
        // 打开一个随机访问文件流，按读写方式
        RandomAccessFile randomFile = new RandomAccessFile(datasPath, "rws");
        randomFile.seek(randomFile.length());
//        FileChannel fileChannel = randomFile.getChannel();
        try {
            int length = bytes.length;
            String str = "";
            if (randomFile.length() == 0) {
                str = "" + length + "\n";
            } else {
                str = "\n" + length + "\n";
            }
//            fileChannel.write(encodeValue(str.getBytes()));
//            ByteBuffer byteBuffer = encodeValue(bytes);
//            fileChannel.write(byteBuffer);
//            fileChannel.close();
            randomFile.write(str.getBytes());
            randomFile.write(bytes);
            randomFile.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
//            fileChannel.close();
            randomFile.close();
        }
        return false;
    }

    @Override
    public <T> boolean updateData(T datas, String tag, Comparator<T> comparator) throws Exception {
        T t = findOneDatas(datas, tag, comparator);
        if (t == null) throw new Exception("数据不存在");
        String datasPath = FileOperatorConfig.dataPath + File.separator + tag + File.separator + tag + ".data";
        RandomAccessFile randomFile = new RandomAccessFile(datasPath, "rws");
        randomFile.seek(0);
        randomFile = operatorTempDatas(randomFile,datasPath,datas, tag, comparator);
        if (randomFile != null) {
            randomFile.close();
        }
        addData(datas, tag, comparator);
        return true;
    }

    @Override
    public <T> boolean deleteData(T datas, String tag, Comparator<T> comparator) throws Exception {
        T t = findOneDatas(datas, tag, comparator);
        if (t == null) return true;
        String datasPath = FileOperatorConfig.dataPath + File.separator + tag + File.separator + tag + ".data";
        RandomAccessFile randomFile = new RandomAccessFile(datasPath, "rws");
        randomFile.seek(0);
        randomFile = operatorTempDatas(randomFile,datasPath,datas, tag, comparator);
        randomFile.close();
        return true;
    }

    @Override
    public <T> T findOneDatas(T datas, String tag, Comparator<T> comparator) throws Exception {
        String datasPath = FileOperatorConfig.dataPath + File.separator + tag + File.separator + tag + ".data";
        // 对象字节已经获取到了,接下来就是进行添加数据了
        // 打开一个随机访问文件流，按读写方式
        RandomAccessFile randomFile = new RandomAccessFile(datasPath, "r");
        randomFile.seek(0);
        boolean check = true;
        while (true) {
            int len = getLength(randomFile, check);
            if (len == -1) {
                randomFile.close();
                return null;
            }
            check = false;
            byte[] bts = new byte[len];
            int le = randomFile.read(bts);
            if (le != len) continue;
            T t = getObjectFromByte(bts, datas, comparator);
            if (t != null) {
                randomFile.close();
                return t;
            }
            if (randomFile.getFilePointer() >= randomFile.length()) break;
        }
        randomFile.close();
        return null;
    }

    @Override
    public <T> List<T> findMulitpleDatas(T datas, String tag, Comparator<T> comparator) throws Exception {
        List<T> dataList = new ArrayList<T>();
        String datasPath = FileOperatorConfig.dataPath + File.separator + tag + File.separator + tag + ".data";
        // 对象字节已经获取到了,接下来就是进行添加数据了
        // 打开一个随机访问文件流，按读写方式
        RandomAccessFile randomFile = new RandomAccessFile(datasPath, "r");
        randomFile.seek(0);
        boolean check = true;
        while (true) {
            int len = getLength(randomFile, check);
            if (len == -1) {
                randomFile.close();
                return dataList;
            }
            check = false;
            byte[] bts = new byte[len];
            int le = randomFile.read(bts);
            if (le != len) continue;
            T t = getObjectFromByte(bts, datas, comparator);
            if (t != null) {
                randomFile.close();
                dataList.add(t);
            }
            if (randomFile.getFilePointer() >= randomFile.length()) break;
        }
        randomFile.close();
        return dataList;
    }
}
