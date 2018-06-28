package bytom.io.entity.transaction;

import java.util.ArrayList;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/28 <br>
 */


public class TransactionListEntity {
    private String ID;
    private long timestamp;
    private String blockID;
    private String blockHeight;
    private int blockTransactionsCount;
    private String confirmation;

    private ArrayList<InputsEntity> inputsList;
    private ArrayList<OutputsEntity> outputsList;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBlockID() {
        return blockID;
    }

    public void setBlockID(String blockID) {
        this.blockID = blockID;
    }

    public String getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(String blockHeight) {
        this.blockHeight = blockHeight;
    }

    public int getBlockTransactionsCount() {
        return blockTransactionsCount;
    }

    public void setBlockTransactionsCount(int blockTransactionsCount) {
        this.blockTransactionsCount = blockTransactionsCount;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public ArrayList<InputsEntity> getInputsList() {
        return inputsList;
    }

    public void setInputsList(ArrayList<InputsEntity> inputsList) {
        this.inputsList = inputsList;
    }

    public ArrayList<OutputsEntity> getOutputsList() {
        return outputsList;
    }

    public void setOutputsList(ArrayList<OutputsEntity> outputsList) {
        this.outputsList = outputsList;
    }

    @Override
    public String toString() {
        return "TransListGroupEntity{" +
                ", ID='" + ID + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", blockID='" + blockID + '\'' +
                ", blockHeight='" + blockHeight + '\'' +
                ", blockTransactionsCount=" + blockTransactionsCount +
                ", confirmation='" + confirmation + '\'' +
                ", inputsList=" + inputsList +
                ", outputsList=" + outputsList +
                '}';
    }

}
