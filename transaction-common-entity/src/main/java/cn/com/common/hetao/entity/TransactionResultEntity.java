package cn.com.common.hetao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 13:14
 *@desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResultEntity implements Serializable {

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
     * 消息
     */
    private String message;

}
