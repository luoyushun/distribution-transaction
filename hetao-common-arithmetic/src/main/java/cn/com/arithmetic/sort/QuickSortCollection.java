package cn.com.arithmetic.sort;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 19:33
 *@desc 这个是快速排序算法
 **/
public interface QuickSortCollection extends SortCollection {

    /**
     * 这个是进行数据交换的数据
     * @param datas
     * @param low
     * @param hight
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> int partitionSort(List<T> datas, int low, int hight, Comparator<T> comparator);

    /**
     * 这个是进行分区的方法
     * @param datas
     * @param low
     * @param hight
     * @param comparator
     * @param <T>
     */
    public <T> void quickSort(List<T> datas, int low, int hight, Comparator<T> comparator);
}
