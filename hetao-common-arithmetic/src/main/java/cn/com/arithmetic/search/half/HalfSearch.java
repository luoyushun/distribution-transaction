package cn.com.arithmetic.search.half;

import cn.com.arithmetic.search.SearchElementCollection;
import cn.com.arithmetic.sort.QuickSortCollection;
import cn.com.arithmetic.sort.quick.QuickSort;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 11:45
 *@desc 折半查找算法首先要保证结合是有序的
 **/
public class HalfSearch implements SearchElementCollection {

    private QuickSortCollection quickSort = new QuickSort();

    public <T> T searchElement(List<T> datas, T element, Comparator<T> comparator) {
        if (datas == null || datas.isEmpty() || element == null || comparator == null) return null;
        // 对datas进行排序
        quickSort.sortCollections(datas, comparator);
        // 进行折半查找
        int low = 0;
        int hight = datas.size() -1;
        while (low <= hight) {
            int mid = (low + hight) / 2;
            if (comparator.compare(datas.get(mid), element) > 0) {
                hight = mid -1;
            }
            if (comparator.compare(datas.get(mid), element) < 0) {
                low = mid + 1;
            }
            if (comparator.compare(datas.get(mid), element) == 0) {
                return datas.get(mid);
            }
        }
        return null;
    }
}
