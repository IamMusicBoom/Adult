package com.wma.adult.base;

import com.wma.adult.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    private ServerConfig serverConfig;


    public String getUrl() {
        return  serverConfig.getUrl();
    }
}
