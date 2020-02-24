package cn.com.arithmetic.sort.straight;

import cn.com.arithmetic.sort.SortCollection;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 16:50
 *@desc 插入排序算法
 **/
public class InsertSort implements SortCollection {

    public <T> void sortCollections(List<T> datas, Comparator<T> comparator) {
        if (datas == null || comparator == null || datas.isEmpty()) return;
        int length = datas.size();
        for (int i=1; i<length; i++) {
            T data = datas.get(i);
            if (comparator.compare(data, datas.get(i-1)) < 0) {
                int j = i-1;
                T temp = datas.get(j);
                for (; comparator.compare(data,temp) < 0 && j >= 0;) {
                    j--;
                    if (j < 0) break;
                    temp = datas.get(j);
                }
                datas.remove(i);
                datas.add(j+1, data);
            }
        }
    }
}
