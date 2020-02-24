package cn.com.arithmetic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 12:20
 *@desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataEntity implements Serializable {

    private Long id;
    private String name;

}
