package cn.com.arithmetic.sort.select;

import cn.com.arithmetic.sort.SelectSortCollection;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 20:54
 *@desc
 **/
public class SelectSort implements SelectSortCollection {
    public <T> int extremumValueSeat(int seat, List<T> datas, Comparator<T> comparator) {
        T temp = datas.get(seat);
        for (int i = seat +1; i< datas.size(); i++) {
            if (comparator.compare(temp, datas.get(i)) > 0) {
                temp = datas.get(i);
                seat = i;
            }
        }
        return seat;
    }

    public <T> void sortCollections(List<T> datas, Comparator<T> comparator) {
        for (int i = 0; i < datas.size(); i++) {
            int seat = extremumValueSeat(i, datas, comparator);
            if (i == seat) continue;
            T temp = datas.get(seat);
            datas.remove(seat);
            datas.add(seat, datas.get(i));
            datas.remove(i);
            datas.add(i, temp);
        }
    }
}
