package com.ansible.www;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/7/16.
 */
@WebServlet(name = "NagiosClServlet",urlPatterns="/NagiosClServlet")
public class NagiosClServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        //nagios添加主机
        String iptext = request.getParameter("iptext");
        if(iptext != null && !"".equals(iptext)){
            String[] lines = iptext.split("\r\n");
            out.println("<textarea rows=\"40\" cols=\"120\">");
            for(int i=0; i<lines.length;i++) {
                out.println("cat <<EOF >/usr/local/nagios/etc/objects/"+lines[i]+".cfg");
                out.println("define host{");
                out.println("\tuse linux-server,host-pnp");
                out.println("\thost_name "+lines[i]);
                out.println("\taddress "+lines[i]);
                out.println("}");

                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+lines[i]);
                out.println("\tservice_description PING");
                out.println("\tcheck_command check_ping!100.0,20%!500.0,60%");
                out.println("}");
                out.println("EOF");
                out.println("echo \"cfg_file=/usr/local/nagios/etc/objects/"+lines[i]+".cfg\" >> /usr/local/nagios/etc/nagios.cfg");
            }
            out.println("</textarea>");
        }

        //添加负载，磁盘，swap，80端口等基础的监控
        String servicetext = request.getParameter("servicetext");
        if(servicetext != null && !"".equals(servicetext)){
            String[] lines = servicetext.split("\r\n");
            out.println("<textarea rows=\"40\" cols=\"120\">");
            for(int i=0; i<lines.length;i++) {
                String ip = lines[i];
                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Load Average");
                out.println("\tcheck_command check_nrpe!check_load\n}\nEOF");

                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Check Disk Root");
                out.println("\tcheck_command check_nrpe!check_disk_root\n}\nEOF");

                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Check Disk Data");
                out.println("\tcheck_command check_nrpe!check_disk_data\n}\nEOF");

                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Check Swap");
                out.println("\tcheck_command check_nrpe!check_swap\n}\nEOF");

                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Check Port 80");
                out.println("\tcheck_command check_tcp!80\n}\nEOF");
            }
            out.println("</textarea>");
        }

        //添加磁盘只读的监控
        String readonly = request.getParameter("readonly");
        if(readonly != null && !"".equals(readonly)){
            String[] lines = readonly.split("\r\n");
            out.println("<textarea rows=\"40\" cols=\"120\">");
            for(int i=0; i<lines.length;i++) {
                String ip = lines[i];
                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description detect root");
                out.println("\tcheck_command check_nrpe!detect_root\n}\nEOF");
            }
            out.println("</textarea>");
        }

        //添加selinux iptables ulimit -n的监控
        String selinux = request.getParameter("selinux");
        if(selinux != null && !"".equals(selinux)){
            String[] lines = selinux.split("\r\n");
            out.println("<textarea rows=\"40\" cols=\"120\">");
            for(int i=0; i<lines.length;i++) {
                String ip = lines[i];
                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description selinux_ulimit_firewall");
                out.println("\tcheck_command check_nrpe!fir_selinux_ulimit\n}\nEOF");
            }
            out.println("</textarea>");
        }

        //添加nginx日志监控，插件自写的。
        String logs = request.getParameter("logs");
        if(logs != null && !"".equals(logs)){
            String[] lines = logs.split("\r\n");
            out.println("<textarea rows=\"40\" cols=\"120\">");
            for(int i=0; i<lines.length;i++) {
                String ip = lines[i];
                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Check Nginx 502");
                out.println("\tcheck_command check_nrpe!check_nginx_log_502\n}\nEOF");

                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Check Nginx 500");
                out.println("\tcheck_command check_nrpe!check_nginx_log_500\n}\nEOF");

                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Check Nginx 503");
                out.println("\tcheck_command check_nrpe!check_nginx_log_503\n}\nEOF");

                out.println("cat <<EOF >>/usr/local/nagios/etc/objects/"+ip+".cfg");
                out.println("define service{");
                out.println("\tuse generic-service,service-pnp");
                out.println("\thost_name "+ip);
                out.println("\tservice_description Check Nginx 404");
                out.println("\tcheck_command check_nrpe!check_nginx_log_404\n}\nEOF");
            }
            out.println("</textarea>");
        }

        //生成添加nagios组的，第一行为组名，第二行为组的全部id。
        String group = request.getParameter("group");
        if(group != null && !"".equals(group)){
            out.println("<textarea rows=\"40\" cols=\"120\">");
            String[] lines = group.split("\r\n");
            String groupName = lines[0];
            String ipall = lines[1];
            out.println("cat <<EOF >/usr/local/nagios/etc/objects/"+groupName+".cfg");
            out.println("define hostgroup{");
            out.println("\thostgroup_name "+groupName);
            out.println("\talias "+groupName);
            out.println("\tmembers  "+ipall);
            out.println("}\nEOF");
            out.println("echo \"cfg_file=/usr/local/nagios/etc/objects/"+groupName+".cfg\" >>/usr/local/nagios/etc/nagios.cfg");
            out.println("</textarea><br />");
        }

        //生成拉取日志的，与nagios无关
        String filename = request.getParameter("filename");
        if(filename != null && !"".equals(filename)){
            out.println("<textarea rows=\"40\" cols=\"120\">");
            String[] lines = filename.split("\r\n");
            for(int i=0; i<lines.length;i++) {
                out.println("cd /tmp");
                out.println("cat /data/zhuangweihong/ansible/sns_frontend_host | while read line;do");
                out.println("scp $line:/tmp/"+lines[i]+" /tmp/"+lines[i]+".${line}");
                out.println("done");
                out.println("tar -zcvf "+lines[i]+".tar.gz /tmp/"+lines[i]+"*");
                out.println("chown yangzheng:yangzheng "+lines[i]+"*");
            }
            out.println("</textarea><br />");
        }

        String nrpe = request.getParameter("nrpe");
        if("nrpe".equals(nrpe)){
            out.println("<br />kill -HUP `ps auxf | grep nrpe | grep -v grep | awk '{print $2}' | head -n1`");
        }else if("nagios".equals(nrpe)){
            out.println("<br />nagioscheck<br />");
            out.println("/etc/init.d/nagios restart");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
