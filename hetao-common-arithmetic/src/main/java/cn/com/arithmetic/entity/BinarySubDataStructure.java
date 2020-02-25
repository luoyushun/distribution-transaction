package cn.com.arithmetic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 12:24
 *@desc 这个是B-数的实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinarySubDataStructure<T> implements Serializable {

    private int length;
    private T firstData;
    private T secondData;
    private T thirdData;
    private BinarySubDataStructure<T> first;
    private BinarySubDataStructure<T> second;
    private BinarySubDataStructure<T> third;
    private BinarySubDataStructure<T> fourth;
    private BinarySubDataStructure<T> parent;

}
