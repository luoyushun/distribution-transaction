package cn.com.arithmetic;

import cn.com.arithmetic.entity.DataEntity;
import cn.com.arithmetic.entity.DataEntityCompare;
import cn.com.arithmetic.entity.SelfCompare;
import cn.com.arithmetic.sort.straight.InsertHalfSort;
import cn.com.arithmetic.sort.straight.InsertSort;

import java.util.ArrayList;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 17:05
 *@desc
 **/
public class InsertSortMain {

    public static void main(String[] args) {
        List<DataEntity> dataEntities = new ArrayList<DataEntity>();
        DataEntityCompare selfCompare = new DataEntityCompare();
        for (int i=0; i< 100; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId((long) i);
            dataEntity.setName("" + Math.floor(Math.random()*100));
            dataEntities.add(dataEntity);
        }
        InsertSort sort = new InsertSort();
        InsertHalfSort halfSort = new InsertHalfSort();
        halfSort.sortCollections(dataEntities, selfCompare);
        System.out.println(dataEntities);
    }

}
