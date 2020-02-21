package cn.com.common.hetao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 16:35
 *@desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseServers {

    private String ip;
    private Integer port;

}
