package cn.com.arithmetic.sort.bubble;

import cn.com.arithmetic.sort.SortCollection;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 19:12
 *@desc 这个是冒泡排序
 **/
public class BubbleSort implements SortCollection {

    public <T> void sortCollections(List<T> datas, Comparator<T> comparator) {
        int length = datas.size();
        for (int i = 0; i < length; i++) {
            T data = datas.get(i);
            for (int j= i + 1; j < length; j++) {
                T temp = datas.get(j);
                if (comparator.compare(data, temp) > 0) {
                    // 这里进行冒泡排序的编写
                    datas.remove(j);
                    datas.add(i,temp);
                    data = temp;
                }
            }
        }
    }
}
