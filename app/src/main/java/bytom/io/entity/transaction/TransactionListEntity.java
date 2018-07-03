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
    private long confirmation;

    private ArrayList<InputsEntity> inputs;
    private ArrayList<OutputsEntity> outputs;

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

    public long getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(long confirmation) {
        this.confirmation = confirmation;
    }

    public ArrayList<InputsEntity> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<InputsEntity> inputs) {
        this.inputs = inputs;
    }

    public ArrayList<OutputsEntity> getOutputs() {
        return outputs;
    }

    public void setOutputs(ArrayList<OutputsEntity> outputs) {
        this.outputs = outputs;
    }

}
