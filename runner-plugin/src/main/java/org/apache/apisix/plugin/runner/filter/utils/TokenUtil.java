package org.apache.apisix.plugin.runner.filter.utils;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;

/**
 * @Package org.apache.apisix.plugin.runner.filter.utils
 * @ClassName TokenUtil
 * @Description TODO
 * @Author Ryan
 * @Date 2023/2/3
 */
public class TokenUtil {

    public static AccessToken parseAccessTokenFromTokenString(String token) {
        AccessToken accessToken;

        try {
            TokenVerifier<AccessToken> tokenVerifier = TokenVerifier
                    .create(token, AccessToken.class);

            accessToken = tokenVerifier.getToken();
            if (!accessToken.isActive()) {
                accessToken = null;
            }
        } catch (VerificationException e) {
//            e.printStackTrace();
            return null;
        }

        return accessToken;
    }
}