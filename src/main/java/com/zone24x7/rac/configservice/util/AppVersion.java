package com.zone24x7.rac.configservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppVersion {

    @Value("${info.application.version}")
    private String version;

    @GetMapping("/v1/application/version")
    public String getVersion() {
        return version;
    }
}
