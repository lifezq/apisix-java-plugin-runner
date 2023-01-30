package org.apache.apisix.plugin.runner.filter.service.impl;

import org.apache.apisix.plugin.runner.filter.dao.UserDAO;
import org.apache.apisix.plugin.runner.filter.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Package org.apache.apisix.plugin.runner.filter.service.impl
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Ryan
 * @Date 2023/1/16
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getByUsername(String username) {
        return UserDAO.findRealmByUsername(username);
    }
}
