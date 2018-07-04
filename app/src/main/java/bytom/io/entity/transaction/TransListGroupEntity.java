package bytom.io.entity.transaction;

import java.util.ArrayList;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/12 <br>
 */



public class TransListGroupEntity {

    private String time;

    private ArrayList<TransListItemEntity> childList;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<TransListItemEntity> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<TransListItemEntity> childList) {
        this.childList = childList;
    }
}
