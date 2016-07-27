package com.ansible.www;

/**
 * Created by Administrator on 2015/10/18.
 */
public class IpInRange {

    public boolean isInRange(String ip, String cidr) {
        if (ip == null || cidr == null || "".equals(ip) || "".equals(cidr)){
            return  false;
        }
        //将源的掩码取出来
        int type2 = Integer.parseInt(ip.replaceAll(".*/", ""));
        int mask2 = 0xFFFFFFFF << (32 - type2);
        //将源ip取出来
        ip = ip.replaceAll("/.*", "");
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        //将目的ip的掩码取出来
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        //如果第一个掩码比较小，直接返回false，无法包含
        if (mask2 < mask){
            return false;
        }
        String cidrIp = cidr.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);

        return (ipAddr & mask) == (cidrIpAddr & mask);
    }

    public boolean isSame(String ip, String cidr) {
        if (ip == null || cidr == null || "".equals(ip) || "".equals(cidr)){
            throw new NullPointerException("ip不能为空");
        }
        //将源的掩码取出来
        int type2 = Integer.parseInt(ip.replaceAll(".*/", ""));
        int mask2 = 0xFFFFFFFF << (32 - type2);
        //将源ip取出来
        ip = ip.replaceAll("/.*", "");
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        //将目的ip的掩码取出来
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        //如果第一个掩码比较小，直接返回false，无法包含
        String cidrIp = cidr.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);

        return (ipAddr & mask) == (cidrIpAddr & mask) && mask == mask2;
    }
}
