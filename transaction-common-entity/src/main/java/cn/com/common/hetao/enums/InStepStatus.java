package cn.com.common.hetao.enums;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 14:48
 *@desc 这个是同步操作的状态
 **/
public enum InStepStatus {

    RELEASE(1),
    GAINRESOURCE(2);

    private Integer value;

    private InStepStatus(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }

}
