package com.ansible.www;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/8/1.
 */
@WebServlet(name = "DomainClServlet",urlPatterns="/DomainClServlet")
public class DomainClServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String alltext = request.getParameter("alltext");
        String type = request.getParameter("type");
        out.println("<pre>");
        if("getGuishudi".equals(type)) {
            String lines[] = alltext.split("\r\n");
            for (int i = 0; i < lines.length; i++) {
                /*URL url = new URL("http://ip.cn/index.php?ip=" + lines[i]);
                HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
                urlcon.connect();
                InputStream is = urlcon.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String l = null;
                while ((l = buffer.readLine()) != null) {
                    Pattern ipreg = Pattern.compile("查询的 IP：.*来自：[^<]*");
                    Matcher ip = ipreg.matcher(l);
                    while (ip.find()) {
                        out.println(ip.group() + "<br />");
                    }
                }
                buffer.close();
                is.close();*/
                /*String path = this.getServletContext().getRealPath("/attachment/17monipdb.dat");
                IP.load(path);
                String guishudi = "";
                String[] ips = IP.find(lines[i]);
                for (String ip: ips){
                    guishudi = guishudi + ip;
                }
                out.println(lines[i]+"\t"+guishudi+"<br />");*/

                String path = this.getServletContext().getRealPath("/attachment/");
                IPSeeker ipSeeker = new IPSeeker("qqwry.dat", path);
                String iWant = ipSeeker.getIPLocation(lines[i]).getCountry()+":"+ipSeeker.getIPLocation(lines[i]).getArea();
                out.println(lines[i]+"\t"+iWant);
            }
            out.println("</pre>");
        }else if ("getAllIp".equals(type)){
            String lines[] = alltext.split("\r\n");
            for (int i = 0; i < lines.length; i++) {
                out.println(lines[i]+":<br />");
                InetAddress[] address = InetAddress.getAllByName(lines[i]);
                for (int j = 0; j < address.length; j++) {
                    out.println(address[j].getHostAddress()+"<br />");
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
