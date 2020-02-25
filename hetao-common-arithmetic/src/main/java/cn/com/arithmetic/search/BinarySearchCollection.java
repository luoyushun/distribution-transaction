package cn.com.arithmetic.search;

import cn.com.arithmetic.entity.TwinBalanceData;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 12:00
 *@desc
 **/
public interface BinarySearchCollection<T> {

    public T searchElement(List<T> datas, T element, Comparator<TwinBalanceData> comparator);

}
