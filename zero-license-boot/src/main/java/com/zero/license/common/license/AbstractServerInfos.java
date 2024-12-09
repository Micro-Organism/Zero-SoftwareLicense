package com.zero.license.common.license;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public abstract class AbstractServerInfos {

    public LicenseCheckModel getServerInfos() {
        LicenseCheckModel result = new LicenseCheckModel();

        try {
            result.setIpAddress(this.getIpAddress());
            result.setMacAddress(this.getMacAddress());
            result.setCpuSerial(this.getCPUSerial());
            result.setMainBoardSerial(this.getMainBoardSerial());
        } catch (Exception e) {
            log.error("获取服务器硬件信息失败", e);
        }

        return result;
    }

    protected abstract List<String> getIpAddress() throws Exception;

    protected abstract List<String> getMacAddress() throws Exception;

    protected abstract String getCPUSerial() throws Exception;

    protected abstract String getMainBoardSerial() throws Exception;

    protected List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>(4);

        // 遍历所有的网络接口
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = networkInterfaces.nextElement();
            // 在所有的接口下再遍历 IP
            for (Enumeration<InetAddress> inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = inetAddresses.nextElement();

                //排除 LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress 类型的 IP 地址
                if (!inetAddr.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()) {
                    result.add(inetAddr);
                }
            }
        }
        return result;
    }

    protected String getMacByInetAddress(InetAddress inetAddr) {
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    stringBuilder.append("-");
                }
                //将十六进制 byte 转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if (temp.length() == 1) {
                    stringBuilder.append("0").append(temp);
                } else {
                    stringBuilder.append(temp);
                }
            }
            return stringBuilder.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
