package cn.com.common.hetao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 12:15
 *@desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDefinationEntity implements Serializable {

    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 微服务名称
     */
    private String micServer;
    /**
     * 微服务ip地址
     */
    private String micServerIp;

    /**
     * 微服务端口号
     */
    private Integer micServerPort;
    /**
     * 资源唯一标识
     */
    private String resourcesId;
    /**
     * 资源名称
     */
    private String resourcesName;
    /**
     * 锁的状态
     */
    private Integer lockStatus;
    /**
     * 属于哪个线程
     */
    private Thread currentThread;

    /**
     * 请求的时间
     */
    private Date requestTime;

    /**
     * 获取锁的时间
     */
    private Date gainLockTime;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 优先级提升的步伐
     */
    private Integer stepPriority;

    /**
     * 多久开始提升优先级
     */
    private Long stepTime;

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 加锁次数
     */
    private Integer times;

}
