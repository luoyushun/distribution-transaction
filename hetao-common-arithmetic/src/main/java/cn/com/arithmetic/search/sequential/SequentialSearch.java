package cn.com.arithmetic.search.sequential;

import cn.com.arithmetic.search.SearchElementCollection;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 11:41
 *@desc 这个是顺序查询方式
 **/
public class SequentialSearch implements SearchElementCollection {

    public <T> T searchElement(List<T> datas, T element, Comparator<T> comparator) {
        if (datas == null || datas.isEmpty() || element == null || comparator == null) return null;
        for (T entity : datas) {
            if (comparator.compare(entity, element) == 0) {
                return entity;
            }
        }
        return null;
    }

}
