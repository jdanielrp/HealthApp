package com.example.healthapp;

public class DatabaseOptions {

    public static final String DB_NAME = "healthapp.db";
    public static final int DB_VERSION = 6;
    public static final String USERS_TABLE = "USERS";
    public static final String ID = "ID";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String DRUGS_TABLE = "DRUGS";
    public static final String ID_DRUG = "_id";
    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String INSERT_USERS_ =
            "INSERT INTO " + USERS_TABLE + " (EMAIL, PASSWORD) values(" +
                    "'jdanielrp@gmail.com'," +
                    "'zakfujih');";

}
