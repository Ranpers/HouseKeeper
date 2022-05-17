package pers.yiran.housekeeper.tools;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class JDBCUtils {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/housekeeper";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "MySQL,020923";

    private static final int MAX_IDLE = 3;
    private static final long MAX_WAIT = 5000;
    private static final int MAX_ACTIVE = 5;
    private static final int INITIAL_SIZE = 10;

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        dataSource.setMaxIdle(MAX_IDLE);
        dataSource.setMaxWaitMillis(MAX_WAIT);
        dataSource.setMaxTotal(MAX_ACTIVE);
        dataSource.setInitialSize(INITIAL_SIZE);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}

