package cn.com.client.hetao.entity;

import cn.com.common.hetao.entity.TransactionDefinationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 0:58
 *@desc
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionExtDefination implements Serializable {

    private TransactionDefinationEntity definationEntity;
    private Thread thread;
    private CountDownLatch countDownLatch;

}
