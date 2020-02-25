package cn.com.arithmetic.search;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 11:40
 *@desc 这个是查询算法
 **/
public interface SearchElementCollection {

    public <T> T searchElement(List<T> datas, T element, Comparator<T> comparator);

}
