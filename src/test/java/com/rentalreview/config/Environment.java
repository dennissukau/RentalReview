package com.rentalreview.config;

import lombok.Data;

@Data
public class Environment {

    public static final String ID = "com.rentalreview.config.Environment";

    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;
}
