package com.ansible.www;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/7/12.
 */
@WebServlet(name = "AnsibleClServlet",urlPatterns="/AnsibleClServlet")
public class AnsibleClServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String host = request.getParameter("host");
        String module = request.getParameter("module");
        String command = request.getParameter("command");
        out.println("ansible "+host+" -m "+module+" -a "+"\""+command+"\""+"<br /><br />");
        String other = request.getParameter("other");
        if("nginx".equals(other)){
            out.println("ansible "+host+" -m shell "+" -a \"/usr/local/nginx/sbin/nginx -t\"<br />");
            out.println("ansible "+host+" -m shell "+" -a \"/usr/local/nginx/sbin/nginx -s reload\"<br />");
        }else if("php-fpm".equals(other)){
            out.println("killall -9 php-fpm<br />");
            out.println("/usr/local/php/sbin/php-fpm<br />");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
