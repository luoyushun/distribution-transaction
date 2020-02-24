package cn.com.arithmetic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 11:04
 *@desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwinBalanceData<D> implements Serializable {

    private TwinBalanceData<D> leftChild;
    private TwinBalanceData<D> rightChild;
    private TwinBalanceData<D> parent;
    private Integer balanceLeef;
    private D date;
    private Boolean taller;

}
