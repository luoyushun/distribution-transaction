package cn.com.arithmetic.sort;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 16:48
 *@desc
 **/
public interface SortCollection {

    public <T> void sortCollections(List<T> datas, Comparator<T> comparator);

}
