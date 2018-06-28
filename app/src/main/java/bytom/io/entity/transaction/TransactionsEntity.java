package bytom.io.entity.transaction;

import java.util.ArrayList;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/27 <br>
 */


public class TransactionsEntity {
    private ArrayList<TransactionListEntity> transactions;

    public ArrayList<TransactionListEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<TransactionListEntity> transactions) {
        this.transactions = transactions;
    }
}
