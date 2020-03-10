package cn.com.hetao.server.enums;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/9 18:55
 *@desc
 **/
public enum CommendsEnum {

    // 这个是删除数据
    DELETE,

    // 这个是如果没有，就保存数据
    NX_SAVE,

    // 如果存在，就修改数据
    EX_SAVE,

    // 查询数据
    FIND,

    // 保存
    SAVE

}
