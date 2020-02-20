package cn.com.common.hetao.enums;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 19:20
 *@desc
 **/
public enum ClientLockDeal {

    REPEATREQUEST(1),
    CLEARLOCK(2);

    private Integer value;

    private ClientLockDeal(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }

}
