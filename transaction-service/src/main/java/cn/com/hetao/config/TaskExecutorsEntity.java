package cn.com.hetao.config;

import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.hetao.netty.ContainorDataDeal;
import cn.com.hetao.transaction.ReceiptDistributionAbs;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.EAN;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/22 11:08
 *@desc 这个是任务列表，其中包含了一些属性和数据信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskExecutorsEntity implements Runnable {

    /**
     * 数据处理的类
     */
    private ReceiptDistributionAbs receiptDistributionAbs;
    /**
     * 这个是容器定义的类
     */
    private List<ContainorDataDeal> definationEntities = new ArrayList<>();

    /**
     * 标识位
     */
    private boolean flag = false;

    /**
     * 这个是添加数据的
     * @param ctx
     * @param entity
     */
    public void addDefinationEntity(ChannelHandlerContext ctx,TransactionDefinationEntity entity) {
        ContainorDataDeal containorDataDeal = new ContainorDataDeal();
        containorDataDeal.setDefinationEntity(entity);
        containorDataDeal.setCtx(ctx);
        synchronized (definationEntities) {
            this.definationEntities.add(containorDataDeal);
            flag = true;
            synchronized (this) {
                this.notify();
            }
        }
    }

    /**
     * 这个是执行业务操作的
     */
    public void run() {
        while (true) {
            if (!flag) {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            synchronized (definationEntities) {
                int size = definationEntities.size();
                if (size == 0) {
                    flag = false;
                }
                for (int i=0; i< size; i++) {
                    ContainorDataDeal containorDataDeal = definationEntities.get(i);
                    definationEntities.remove(i);
                    i --;
                    size = definationEntities.size();
                    receiptDistributionAbs.receiptDataAndDeal(containorDataDeal.getCtx(), containorDataDeal.getDefinationEntity());
                }
                flag = false;
            }
        }
    }
}
