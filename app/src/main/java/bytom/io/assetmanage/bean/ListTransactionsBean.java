package bytom.io.assetmanage.bean;

import java.util.List;

/**
 * Created by Nil on 2018/6/28
 */
public class ListTransactionsBean {

    private List<TransactionsBean> transactions;

    public List<TransactionsBean> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionsBean> transactions) {
        this.transactions = transactions;
    }

    public static class TransactionsBean {
        /**
         * ID : c4563045aa025965af6268569e817b68d0ae537dfdd7755e693b877133683a71
         * timestamp : 1527917283
         * blockID : 1f54dbe75104e2c5d471e6b1b8f6184ae8b6824cf3e6f95e9142b535d472197c
         * blockHeight : 29969
         * blockTransactionsCount : 2
         * confirmation : 17379
         * inputs : [{"type":"spend","assetID":"ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff","amount":"2799563000","address":"bm1qcmsd8vgnj4vaaeuvtx8nsmzg8pq9cjdyae7y2h","spentOutputID":"bd94675bb2f117a46cc6ece1805bdf11b2920eabc1acf58fb858258b07e22afc"}]
         * outputs : [{"type":"control","assetID":"ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff","amount":"99126400","address":"bm1qd0uua3jl0jlyq3vk7pzhwdwtu0vxjr4qxlcr4q","OutputID":"c4403964ccba30caf6bc72b6b5a361da35ebc8be464057c1b297d3818c5faff5"},{"type":"control","assetID":"ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff","amount":"2700000000","address":"bm1q5p9d4gelfm4cc3zq3slj7vh2njx23ma2cf866j","OutputID":"dd56f223f884e60ec7cb83de1c4a1a067ab2ed7206f4ba05e0356d95a2a81f93","position":1}]
         */

        private String ID;
        private long timestamp;
        private String blockID;
        private String blockHeight;
        private int blockTransactionsCount;
        private String confirmation;
        private List<InputsBean> inputs;
        private List<OutputsBean> outputs;

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

        public List<InputsBean> getInputs() {
            return inputs;
        }

        public void setInputs(List<InputsBean> inputs) {
            this.inputs = inputs;
        }

        public List<OutputsBean> getOutputs() {
            return outputs;
        }

        public void setOutputs(List<OutputsBean> outputs) {
            this.outputs = outputs;
        }

        public static class InputsBean {
            /**
             * type : spend
             * assetID : ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
             * amount : 2799563000
             * address : bm1qcmsd8vgnj4vaaeuvtx8nsmzg8pq9cjdyae7y2h
             * spentOutputID : bd94675bb2f117a46cc6ece1805bdf11b2920eabc1acf58fb858258b07e22afc
             */

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

        public static class OutputsBean {
            /**
             * type : control
             * assetID : ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
             * amount : 99126400
             * address : bm1qd0uua3jl0jlyq3vk7pzhwdwtu0vxjr4qxlcr4q
             * OutputID : c4403964ccba30caf6bc72b6b5a361da35ebc8be464057c1b297d3818c5faff5
             * position : 1
             */

            private String type;
            private String assetID;
            private String amount;
            private String address;
            private String OutputID;
            private int position;

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

            public String getOutputID() {
                return OutputID;
            }

            public void setOutputID(String OutputID) {
                this.OutputID = OutputID;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }
        }
    }
}
