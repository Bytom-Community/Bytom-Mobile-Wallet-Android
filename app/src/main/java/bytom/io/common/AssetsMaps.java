package bytom.io.common;

import bytom.io.R;

/**
 * Created by DongFangZhou on 2018/6/28.
 */

public class AssetsMaps {
    private static int DEFAULT_ICON = R.mipmap.diamonds;

    private static String BTM_ID = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
    private static String BTM_NAME = "BTM";
    private static int BTM_ICON = R.mipmap.bytom;






    public static String getAssetsName(String assetID){
        if (BTM_ID.equals(assetID)){
            return BTM_NAME;
        }else{
            return formatId(assetID);
        }
    }

    public static int getAssetsIcon(String assetID){
        if (BTM_ID.equals(assetID)){
            return BTM_ICON;
        }else{
            return DEFAULT_ICON;
        }
    }

    private static String formatId(String assetID){
        if (assetID == null){
            return "";
        }else if (assetID.length()<10){
            return assetID;
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append(assetID.substring(0,5)).append("…………").append(assetID.substring(assetID.length()-5,assetID.length()));
            return sb.toString();
        }
    }
}
