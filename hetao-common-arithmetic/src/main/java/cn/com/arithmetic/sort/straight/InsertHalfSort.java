package cn.com.arithmetic.sort.straight;

import cn.com.arithmetic.sort.SortCollection;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 18:06
 *@desc 插入排序中的折半插入法
 **/
public class InsertHalfSort implements SortCollection {
    public <T> void sortCollections(List<T> datas, Comparator<T> comparator) {
        if (datas == null || comparator == null || datas.isEmpty()) return;
        int length = datas.size();
        for (int i=1; i<length; i++) {
            T data = datas.get(i);
            if (comparator.compare(data, datas.get(i - 1)) < 0) {
                int hight = i;
                int low = 0;
                while (low < hight) {
                    int seat = (hight + low) / 2;
                    if (comparator.compare(data, datas.get(seat)) < 0) {
                        hight = seat == 0 ? 0:seat - 1;
                    } else {
                        low = seat + 1;
                    }
                }
                int seat = -1;
                if (comparator.compare(data, datas.get(hight)) < 0) {
                    seat = hight;
                } else if (comparator.compare(data, datas.get(low)) < 0) {
                    seat = low;
                } else {
                    seat = low + 1;
                }
                datas.remove(i);
                datas.add(seat, data);
            }
        }
    }
}
