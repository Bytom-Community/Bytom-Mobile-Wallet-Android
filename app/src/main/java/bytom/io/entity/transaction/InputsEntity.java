package bytom.io.entity.transaction;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/27 <br>
 */


public class InputsEntity {
    private String type;
    private String assetID;
    private String amount;
    private String address;
    private String spentOutputID;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpentOutputID() {
        return spentOutputID;
    }

    public void setSpentOutputID(String spentOutputID) {
        this.spentOutputID = spentOutputID;
    }
}
