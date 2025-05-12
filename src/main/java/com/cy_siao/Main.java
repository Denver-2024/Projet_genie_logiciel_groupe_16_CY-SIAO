package com.cy_siao;

import com.cy_siao.util.DatabaseUtil;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        DatabaseUtil dbUtil = new DatabaseUtil();
        dbUtil.getConnection();
        System.out.println("Connected");
    }
}
