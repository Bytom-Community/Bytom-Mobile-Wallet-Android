package bytom.io.entity.transaction;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/12 <br>
 */



public class TransListItemEntity {
    private String icon;
    private String addr;
    private String classify;
    private String time;
    private String num;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
