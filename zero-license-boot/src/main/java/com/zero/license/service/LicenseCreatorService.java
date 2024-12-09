/**
 * Project Name: true-license
 * File Name: LicenseCreatorService
 * Package Name: com.example.demo.service
 * Date: 2020/10/10 13:44
 * Author: 方瑞冬
 */
package com.zero.license.service;

import com.zero.license.common.license.LicenseCheckModel;
import com.zero.license.common.license.LicenseCreatorParam;

import java.util.Map;

public interface LicenseCreatorService {

    LicenseCheckModel getServerInfos(String osName);

    Map<String, Object> generateLicense(LicenseCreatorParam param);
}
