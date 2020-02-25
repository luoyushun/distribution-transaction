package cn.com.arithmetic.binary;

import cn.com.arithmetic.entity.BinarySubDataStructure;

import java.util.Comparator;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 12:24
 *@desc
 **/
public interface BinarySub {

    /**
     * 这个是插入B-树的代码
     * @param parent
     * @param element
     * @param comparator
     * @param <T>
     * @return
     */
    public <T> boolean insertElement(BinarySubDataStructure<T> parent, T element, Comparator<T> comparator);

    public <T> void justifyAdapt(BinarySubDataStructure<T> dataStructure, Comparator<T> comparator);

    public <T> void adaptStructure(BinarySubDataStructure<T> dataStructure, Comparator<T> comparator);

    public <T> T findElement(BinarySubDataStructure<T> parent, T element, Comparator<T> comparator);

}
