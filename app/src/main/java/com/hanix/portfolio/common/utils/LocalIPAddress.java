package com.hanix.portfolio.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class LocalIPAddress {

    public LocalIPAddress() {
    }

    /**
     * ip4 가져오기
     *
     * @return
     */
    public String getLocalIp4Address() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration<InetAddress> enumIpaddr = intf.getInetAddresses(); enumIpaddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpaddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address)
                        return inetAddress.getHostAddress().toString();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }

}
