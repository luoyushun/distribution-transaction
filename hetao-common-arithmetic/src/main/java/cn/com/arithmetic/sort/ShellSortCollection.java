package cn.com.arithmetic.sort;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 18:44
 *@desc 这个是希尔排序
 **/
public interface ShellSortCollection {

    public <T> void shellSort(List<T> datas, List<Integer> dks, Comparator<T> comparator);

    public <T> void sortCollections(List<T> datas, Integer dk, Comparator<T> comparator);
}
