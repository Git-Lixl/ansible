package com.ansible.www;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/8/1.
 */
@WebServlet(name = "RegClServlet",urlPatterns="/RegClServlet")
public class RegClServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String alltext = request.getParameter("alltext");
        String type = request.getParameter("type");
        if("getIp".equals(type)){
            String[] lines = alltext.split("\r\n");
            for(int i=0; i<lines.length;i++){
                //从文本中提取出ip地址，一个ip地址一行
                Pattern ipreg = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
                Matcher ip = ipreg.matcher(lines[i]);
                while(ip.find()){
                    out.println(ip.group()+"<br />");
                }
            }
        }else if ("getConf".equals(type)){
            String[] lines = alltext.split("\r\n");
            out.println("<textarea rows=\"25\" cols=\"150\">");
            for(int i=0; i<lines.length;i++){
                //提取出有效的配置信息，忽略#;和空行：
                Pattern ipreg = Pattern.compile("^\\s*#.*|^\\s*$|^\\s*;.*");
                Matcher ip = ipreg.matcher(lines[i]);
                if(!ip.find()){
                    out.println(lines[i]);
                }
            }
            out.println("</textarea>");
        }else if("getMail".equals(type)){
            String[] lines = alltext.split("\r\n");
            for(int i=0; i<lines.length;i++){
                //从文本中提取出邮箱，一个邮箱一行
                Pattern ipreg = Pattern.compile("[a-z0-9_]+@[a-z0-9]+\\.[a-z]+");
                Matcher ip = ipreg.matcher(lines[i]);
                while(ip.find()){
                    out.println(ip.group()+"<br />");
                }
            }
        }else if ("getDomain".equals(type)){
            String[] lines = alltext.split("\r\n");
            for(int i=0; i<lines.length;i++){
                //从文本中提取出域名，一个域名一行
                Pattern ipreg = Pattern.compile("[0-9a-zA-Z]+[0-9a-zA-Z\\.-]*\\.[a-zA-Z]{2,4}");
                Matcher ip = ipreg.matcher(lines[i]);
                while(ip.find()){
                    out.println(ip.group()+"<br />");
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
