package bytom.io.common;

import java.util.Map;

/**
 * Created by DongFangZhou on 2018/6/28.
 */

public class UrlConfig {
    private static String BASE_URL = "http://39.104.187.161:9888/v1/";

    private static String URL_LIST_ASSETS = BASE_URL + "list-assets";
    private static String URL_LIST_TRANSACTIONS = BASE_URL + "list-transactions";

    private static String parameterToString(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }


    public static String assetsList(Map<String, String> map) {
        return BASE_URL + "list-assets" + parameterToString(map);
    }

    public static String listTransactions(Map<String, String> params) {
        //return BASE_URL + "list-transactions" + parameterToString(params);
        return URL_LIST_TRANSACTIONS;
    }
}
