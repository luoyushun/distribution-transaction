package cn.com.hetao.io.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/29 15:24
 *@desc 这个是键的定义
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyObjectDefination implements Serializable {

    private String name;
    private Long point;
    private Long length;
    private Long timeout;

}
