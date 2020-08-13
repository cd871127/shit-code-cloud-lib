package com.shit.code.cloud.common;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Anthony Chen
 * @date 2020/3/17
 **/
public class NetUtils {
    private NetUtils() {
    }

    /**
     * 得到真实的IP
     *
     * @return ip列表
     */
    public static List<String> getRealIpAddress() {
        List<String> ipList = new ArrayList<>(3);
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = networkInterfaces.nextElement();
                if (!(netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp())) {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (!ip.isLoopbackAddress() && !ip.isLinkLocalAddress()
                                && ip.isSiteLocalAddress() && ip instanceof Inet4Address) {
                            ipList.add(ip.getHostAddress());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipList;
    }

}
