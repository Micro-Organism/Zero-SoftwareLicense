package com.zero.license.controller;

import com.zero.license.common.license.LicenseCheckModel;
import com.zero.license.common.license.LicenseCreatorParam;
import com.zero.license.service.LicenseCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/license")
public class LicenseCreatorController {
    @Autowired
    private LicenseCreatorService licenseCreatorService;

    @GetMapping("/getServerInfos")
    public LicenseCheckModel getServerInfos(@RequestParam String osName) {
        return licenseCreatorService.getServerInfos(osName);
    }

    @PostMapping("/generateLicense")
    public Map<String, Object> generateLicense(@RequestBody LicenseCreatorParam param) {
        return licenseCreatorService.generateLicense(param);
    }
}
