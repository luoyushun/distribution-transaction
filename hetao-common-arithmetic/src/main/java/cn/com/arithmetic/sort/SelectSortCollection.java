package cn.com.arithmetic.sort;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 20:51
 *@desc
 **/
public interface SelectSortCollection extends SortCollection {

    /**
     * 选择极值
     * @param seat
     * @param datas
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> int extremumValueSeat(int seat, List<T> datas, Comparator<T> comparator);

}
