package bytom.io.entity.transaction;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/27 <br>
 */


public class OutputsEntity {
    private String type;
    private String assetID;
    private long amount;
    private String address;
    private String OutputID;

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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOutputID() {
        return OutputID;
    }

    public void setOutputID(String outputID) {
        OutputID = outputID;
    }
}
