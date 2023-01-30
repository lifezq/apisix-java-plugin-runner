package org.apache.apisix.plugin.runner.filter.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Package org.apache.apisix.plugin.runner.filter.dao
 * @ClassName UserDAO
 * @Description TODO
 * @Author Ryan
 * @Date 2023/1/16
 */
public class UserDAO {
    private final static JdbcTemplate JDBCTEMPLATE;

    static {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("192.168.2.7");
        dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDatabaseName("keycloak");
        JDBCTEMPLATE = new JdbcTemplate(dataSource);
    }

    public static String findRealmByUsername(String username) {
        return JDBCTEMPLATE.queryForObject(
                "SELECT t2.NAME FROM `user_entity` AS `t1` LEFT JOIN `realm` AS `t2` ON t1.REALM_ID=t2.ID WHERE t1.USERNAME='" +
                        username + "' LIMIT 1", String.class);
    }
}
