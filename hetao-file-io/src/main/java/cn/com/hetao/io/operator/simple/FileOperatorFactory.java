package cn.com.hetao.io.operator.simple;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 13:39
 *@desc 这个是文件操作的工程类
 **/
public interface FileOperatorFactory {

    /**
     * 添加对象数据
     * @param datas
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> boolean addDatas(T datas, Comparator<T> comparator) throws Exception;

    /**
     * 添加对象数据
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> boolean addDatas(T datas, String tag, Comparator<T> comparator) throws Exception;

    /**
     * 删除数据
     * @param datas
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> boolean deleteDatas(T datas, Comparator<T> comparator) throws Exception;

    /**
     * 删除数据
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> boolean deleteDatas(T datas, String tag, Comparator<T> comparator) throws Exception;

    /**
     * 查询单个数据
     * @param datas
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> T findOneDatas(T datas, Comparator<T> comparator) throws Exception;

    /**
     * 查询单个数据
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> T findOneDatas(T datas, String tag, Comparator<T> comparator) throws Exception;

    /**
     * 查询多个数据
     * @param datas
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> List<T> findMultipleDatas(T datas, Comparator<T> comparator);

    /**
     * 查询多个数据
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> List<T> findMultipleDatas(T datas, String tag, Comparator<T> comparator);

    /**
     * 更新数据
     * @param datas
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> boolean updateDatas(T datas, Comparator<T> comparator) throws Exception;

    /**
     * 更新数据
     * @param datas
     * @param tag
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> boolean updateDatas(T datas, String tag, Comparator<T> comparator) throws Exception;

}
