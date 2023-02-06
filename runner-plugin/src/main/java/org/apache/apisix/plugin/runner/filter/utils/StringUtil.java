package org.apache.apisix.plugin.runner.filter.utils;

import java.util.Arrays;

/**
 * @Package org.apache.apisix.plugin.runner.filter.utils
 * @ClassName StringUtils
 * @Description TODO
 * @Author Ryan
 * @Date 2023/2/3
 */
public class StringUtil {
    public static String parseTokenFromCookie(String cookieValues) {
        String[] ns = cookieValues.split("; ");
        return Arrays.stream(ns).filter(x -> {
            String[] values = x.split("=");
            if (values.length == 2 && values[0].equals("token")) {
                return true;
            }
            return false;
        })
                .map(x -> {
                    String[] values = x.split("=");
                    if (values.length == 2 && values[0].equals("token")) {
                        return values[1];
                    }

                    return null;
                })
                .findFirst().orElse(null);
    }

    public static String rewritePathWithRealm(String path, String realm) {
        String[] ns = path.split("/");
        Arrays.stream(ns).forEach(System.out::println);
        if (ns.length >= 2) {
            if (!"account-service".equals(ns[1])) {
                path = path.replaceFirst("/" + ns[1], "/" + ns[1] + "-" + realm);
            }
        }
        return path;
    }

    public static String getRealmFromIssuer(String issuer) {
        return issuer.substring(issuer.lastIndexOf("/") + 1);
    }
}
