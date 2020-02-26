package cn.com.hetao.io.operator.simple;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 14:16
 *@desc
 **/
public interface DataFileOperator {

    /**
     * 向文件中添加数据信息
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> boolean addData(T datas, String tag, Comparator<T> comparator) throws Exception;

    /**
     * 更新文件中的数据信息
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> boolean updateData(T datas, String tag, Comparator<T> comparator) throws Exception;

    /**
     * 删除文件中的数据信息
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> boolean deleteData(T datas, String tag, Comparator<T> comparator) throws Exception;

    /**
     * 查询单个数据信息
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T findOneDatas(T datas, String tag, Comparator<T> comparator) throws Exception;

    /**
     * 查询多个数据信息
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T>List<T> findMulitpleDatas(T datas, String tag, Comparator<T> comparator) throws Exception;

}
