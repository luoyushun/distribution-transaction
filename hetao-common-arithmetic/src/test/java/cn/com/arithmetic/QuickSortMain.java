package cn.com.arithmetic;

import cn.com.arithmetic.entity.DataEntity;
import cn.com.arithmetic.entity.DataEntityCompare;
import cn.com.arithmetic.sort.quick.QuickSort;

import java.util.ArrayList;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 19:50
 *@desc
 **/
public class QuickSortMain {

    public static void main(String[] args) {
        List<DataEntity> dataEntities = new ArrayList<DataEntity>();
        DataEntityCompare selfCompare = new DataEntityCompare();
        for (int i=0; i< 100; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId((long) i);
            dataEntity.setName("" + Math.floor(Math.random()*100));
            dataEntities.add(dataEntity);
        }
        QuickSort quickSort = new QuickSort();
        quickSort.sortCollections(dataEntities, selfCompare);
        System.out.println(dataEntities);
    }

}
