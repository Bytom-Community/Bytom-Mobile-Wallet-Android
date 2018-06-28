package bytom.io.home.bean;

import java.util.List;

/**
 * Created by DongFangZhou on 2018/6/28.
 */

public class HomeAssetsBean {

    private List<AssetsBean> assets;

    public List<AssetsBean> getAssets () {
        return assets;
    }

    public void setAssets (List<AssetsBean> assets) {
        this.assets = assets;
    }

    public static class AssetsBean {
        /**
         * assetID : 697e0d26c61a7d660a4eec5b45b95e9dba6d9a3b133a3da4d30e1ddb98466f28
         * amount : 50000000000
         */

        private String assetID;
        private String amount;

        public String getAssetID () {
            return assetID;
        }

        public void setAssetID (String assetID) {
            this.assetID = assetID;
        }

        public String getAmount () {
            return amount;
        }

        public void setAmount (String amount) {
            this.amount = amount;
        }
    }
}
