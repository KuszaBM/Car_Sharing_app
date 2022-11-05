package org.carsharing;

public class SqlStatementsCreator {

    public static String createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR UNIQUE NOT NULL);";
        return sql;
    }

}
