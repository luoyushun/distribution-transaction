package cn.com.arithmetic.sort.quick;

import cn.com.arithmetic.sort.QuickSortCollection;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 19:38
 *@desc 这个是快速排序算法
 **/
public class QuickSort implements QuickSortCollection {

    public <T> int partitionSort(List<T> datas, int low, int hight, Comparator<T> comparator) {
        T data = datas.get(low);
        while (low < hight) {
            while(low < hight && comparator.compare(data, datas.get(hight)) <= 0) --hight;
            T temp = datas.get(hight);
            datas.remove(hight);
            datas.add(hight, data);
            datas.remove(low);
            datas.add(low, temp);
            while (low < hight && comparator.compare(data, datas.get(low)) > 0) ++low;
            temp = datas.get(low);
            datas.remove(hight);
            datas.add(hight, temp);
            datas.remove(low);
            datas.add(low, data);
        }
        return low;
    }

    public <T> void quickSort(List<T> datas, int low, int hight, Comparator<T> comparator) {
        if (low < hight) {
            int pivotloc = partitionSort(datas, low, hight, comparator);
            quickSort(datas, low, pivotloc - 1, comparator);
            quickSort(datas, pivotloc+1, hight, comparator);
        }
    }

    public <T> void sortCollections(List<T> datas, Comparator<T> comparator) {
        int low = 0;
        int height = datas.size() - 1;
        quickSort(datas, low, height, comparator);
    }
}
