package org.apache.apisix.plugin.runner.filter;

import org.apache.apisix.plugin.runner.HttpRequest;
import org.apache.apisix.plugin.runner.HttpResponse;
import org.apache.apisix.plugin.runner.filter.service.impl.UserServiceImpl;
import org.apache.apisix.plugin.runner.filter.utils.StringUtil;
import org.apache.apisix.plugin.runner.filter.utils.TokenUtil;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package org.apache.apisix.plugin.runner.filter
 * @ClassName AppRewriteRequestRealmFilter
 * @Description TODO
 * @Author Ryan
 * @Date 2023/2/3
 */
@Component
public class AppRewriteRequestRealmFilter implements PluginFilter {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public String name() {
        /* It is recommended to keep the name of the filter the same as the class name.
         Configure the filter to be executed on apisix's routes in the following format

        {
            "uri": "/hello",
            "plugins": {
                "ext-plugin-pre-req": {
                    "conf": [{
                        "name": "RewriteRequestDemoFilter",
                        "value": "bar"
                    }]
                }
            },
            "upstream": {
                "nodes": {
                    "127.0.0.1:1980": 1
                },
                "type": "roundrobin"
            }
        }

        The value of name in the configuration corresponds to the value of return here.
         */

        return "AppRewriteRequestRealmFilter";
    }

    @Override
    public void filter(HttpRequest request, HttpResponse response, PluginFilterChain chain) {
        /*
         * If the conf you configured is of type json, you can convert it to Map or json.
         */
//
//        String configStr = request.getConfig(this);
//        Gson gson = new Gson();
//        Map<String, String> user = new HashMap<>();
//                user = gson.fromJson(request.getBody(), user.getClass());
//        String jsessionid = request.getArg("JSESSIONID");

        AccessToken accessToken = TokenUtil.parseAccessTokenFromTokenString(
                StringUtil.parseTokenFromCookie(request.getHeader("cookie")));
        if (accessToken != null) {
            request.setPath(StringUtil.rewritePathWithRealm(request.getPath(),
                    StringUtil.getRealmFromIssuer(accessToken.getIssuer())));
        }
        
        /*
         * You can use the parameters in the configuration.
         */

        // note: the path to the rewrite must start with '/'
//        request.setPath((String) conf.get("rewrite_path"));
//        request.setHeader((String) conf.get("conf_header_name"), (String) conf.get("conf_header_value"));
//        /* note: The value of the parameter is currently a string type.
//                 If you need the json type, you need the upstream service to parse the string value to json.
//                 For example, if the arg is set as below
//                 request.setArg("new arg", "{\"key1\":\"value1\",\"key2\":2}");
//
//                 The arg received by the upstream service will be as below
//                 "new arg": "{\"key1\":\"value1\",\"key2\":2}"
//         */
//        request.setArg((String) conf.get("conf_arg_name"), (String) conf.get("conf_arg_value"));
//
//        /*
//         * You can fetch the Nginx variables, and the request body
//         */
//        String remoteAddr = request.getVars("remote_addr");
//        String serverPort = request.getVars("server_port");
//        String body = request.getBody();
//
        chain.filter(request, response);
    }

    /**
     * If you need to fetch some Nginx variables in the current plugin, you will need to declare them in this function.
     *
     * @return a list of Nginx variables that need to be called in this plugin
     */
    @Override
    public List<String> requiredVars() {
        List<String> vars = new ArrayList<>();
        vars.add("remote_addr");
        vars.add("server_port");
        return vars;
    }

    /**
     * If you need to fetch request body in the current plugin, you will need to return true in this function.
     */
    @Override
    public Boolean requiredBody() {
        return true;
    }
}
