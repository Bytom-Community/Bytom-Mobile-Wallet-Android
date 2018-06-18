package bytom.io.assetmanage.bean;

/**
 * Created by Nil on 2018/6/18
 */
public class TransferBean {
    private int transferType;
    private String transferAddress;
    private String transferTime;
    private String numberOfTransactions;
    private boolean transferSuccess;

    public TransferBean() {
    }

    public TransferBean(int transferType, String transferAddress, String transferTime,
                        String numberOfTransactions, boolean transferSuccess) {
        this();
        this.transferType = transferType;
        this.transferAddress = transferAddress;
        this.transferTime = transferTime;
        this.numberOfTransactions = numberOfTransactions;
        this.transferSuccess = transferSuccess;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public String getTransferAddress() {
        return transferAddress;
    }

    public void setTransferAddress(String transferAddress) {
        this.transferAddress = transferAddress;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(String numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public boolean isTransferSuccess() {
        return transferSuccess;
    }

    public void setTransferSuccess(boolean transferSuccess) {
        this.transferSuccess = transferSuccess;
    }
}
