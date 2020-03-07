package cn.com.hetao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/29 15:05
 *@desc 这个是值的对象的定义
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValueObjectDefination implements Serializable {

    /**
     * 创建的时间
     */
    private Date createTime;
    /**
     * 最后访问的时间
     */
    private Date lastAccessTime;

    /**
     * 访问的次数
     */
    private Long times;

    /**
     * 这个是键值
     */
    private String key;

    /**
     * 这个是值
     */
    private Object value;

}
