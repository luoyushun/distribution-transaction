package cn.com.common.hetao.enums;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 18:39
 *@desc
 **/
public enum ReleaseStatus {
    NORMAL(11),
    UNNORMAL(12),
    CANCEL(13),
    TIMEOUT(14);
    private Integer value;
    private ReleaseStatus(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }
}
