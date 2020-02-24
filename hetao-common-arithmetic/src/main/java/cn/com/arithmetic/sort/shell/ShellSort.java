package cn.com.arithmetic.sort.shell;

import cn.com.arithmetic.sort.ShellSortCollection;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 18:47
 *@desc 这个是希尔排序的实现
 **/
public class ShellSort implements ShellSortCollection {

    public <T> void shellSort(List<T> datas, List<Integer> dks, Comparator<T> comparator) {
        for (Integer dk : dks) {
            sortCollections(datas, dk, comparator);
        }
    }

    public <T> void sortCollections(List<T> datas, Integer dk, Comparator<T> comparator) {
        int length = datas.size();
        for (int i = dk; i < length; i++) {
            T data = datas.get(i);
            if (comparator.compare(data, datas.get(i-dk)) < 0) {
                int j = i - dk;
                for (; j>=0 & comparator.compare(data,datas.get(j)) < 0; ) {
                    j-=dk;
                    if (j < 0) break;
                }
                int seat = j + dk;
                datas.remove(i);
                datas.add(seat, data);
            }
        }
    }
}
