package cn.com.hetao.server.entity;

import cn.com.hetao.server.enums.CommendsEnum;
import cn.com.hetao.server.enums.NoticeStartEnum;
import cn.com.hetao.server.enums.RequestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/9 17:01
 *@desc 这个是通知的实体
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeEntity implements Serializable {

    /**
     * 这个是数据键
     */
    private String key;
    /**
     * 这个是主键
     */
    private Long id;
    /**
     * 这个是地址
     */
    private String ip;
    /**
     * 这个是优先级
     */
    private Integer priority;
    /**
     * 这个是请求状态
     */
    private RequestStatusEnum status;

    /**
     * 这个是请求的状态
     */
    private NoticeStartEnum isStart;

    /**
     * 这个是命令集
     */
    private CommendsEnum commend;

    /**
     * 这个是时间
     */
    private Long timeout;

}
