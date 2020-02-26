package cn.com.hetao.io.operator.simple;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 13:41
 *@desc 文件操作信息
 **/
public abstract class FileOperatorFactoryAbs implements FileOperatorFactory {

    protected String defaultTage = "default";

    protected DataFileOperator dataFileOperator = new SimpleDataFileOperator();

    public <T> boolean addDatas(T datas, Comparator<T> comparator) throws Exception {
        return addDatas(datas, defaultTage, comparator);
    }

    public <T> boolean addDatas(T datas, String tag, Comparator<T> comparator) throws Exception {
        return dataFileOperator.addData(datas, tag, comparator);
    }

    public <T> boolean deleteDatas(T datas, Comparator<T> comparator) throws Exception {
        return deleteDatas(datas, defaultTage, comparator);
    }

    public <T> boolean deleteDatas(T datas, String tag, Comparator<T> comparator) throws Exception {
        return dataFileOperator.deleteData(datas, tag, comparator);
    }

    public <T> T findOneDatas(T datas, Comparator<T> comparator) throws Exception {
        return findOneDatas(datas, defaultTage, comparator);
    }

    public <T> T findOneDatas(T datas, String tag, Comparator<T> comparator) throws Exception {
        return dataFileOperator.findOneDatas(datas, tag, comparator);
    }

    public <T> List<T> findMultipleDatas(T datas, String tag, Comparator<T> comparator) {
        return null;
    }

    public <T> List<T> findMultipleDatas(T datas, Comparator<T> comparator) {
        return findMultipleDatas(datas, defaultTage, comparator);
    }

    public <T> boolean updateDatas(T datas, Comparator<T> comparator) throws Exception {
        return updateDatas(datas, defaultTage, comparator);
    }

    public <T> boolean updateDatas(T datas, String tag, Comparator<T> comparator) throws Exception {
        return dataFileOperator.updateData(datas, tag, comparator);
    }
}
