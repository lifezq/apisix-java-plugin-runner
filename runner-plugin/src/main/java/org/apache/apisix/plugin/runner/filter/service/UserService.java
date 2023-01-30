package org.apache.apisix.plugin.runner.filter.service;

/**
 * @Package org.apache.apisix.plugin.runner.filter.service
 * @ClassName UserService
 * @Description TODO
 * @Author Ryan
 * @Date 2023/1/16
 */
public interface UserService {
    String getByUsername(String username);
}
