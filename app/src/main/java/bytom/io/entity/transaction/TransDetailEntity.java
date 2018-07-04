package bytom.io.entity.transaction;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/7/3 <br>
 */


public class TransDetailEntity {
    private String totalNeu;
    private String storageNeu;
    private String vmNeu;

    public String getTotalNeu() {
        return totalNeu;
    }

    public void setTotalNeu(String totalNeu) {
        this.totalNeu = totalNeu;
    }

    public String getStorageNeu() {
        return storageNeu;
    }

    public void setStorageNeu(String storageNeu) {
        this.storageNeu = storageNeu;
    }

    public String getVmNeu() {
        return vmNeu;
    }

    public void setVmNeu(String vmNeu) {
        this.vmNeu = vmNeu;
    }
}
