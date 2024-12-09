package com.zero.license.service.impl;

import com.zero.license.common.license.*;
import com.zero.license.service.LicenseCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class LicenseCreatorServiceImpl implements LicenseCreatorService {

    private final LicenseConfig licenseConfig;

    @Autowired
    public LicenseCreatorServiceImpl(LicenseConfig licenseConfig) {
        this.licenseConfig = licenseConfig;
    }

    @Override
    public LicenseCheckModel getServerInfos(String osName) {
        //操作系统类型
        if (StringUtils.isEmpty(osName)) {
            osName = System.getProperty("os.name");
        }
        osName = osName.toLowerCase();

        AbstractServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        } else {//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }

    @Override
    public Map<String, Object> generateLicense(LicenseCreatorParam param) {
        Map<String, Object> resultMap = new HashMap<>(2);

        if (StringUtils.isEmpty(param.getLicensePath())) {
            param.setLicensePath(licenseConfig.getLicensePath());
        }

        LicenseCreator licenseCreator = new LicenseCreator(param);
        boolean result = licenseCreator.generateLicense();

        if (result) {
            resultMap.put("result", "ok");
            resultMap.put("msg", param);
        } else {
            resultMap.put("result", "error");
            resultMap.put("msg", "证书文件生成失败！");
        }

        return resultMap;
    }
}
