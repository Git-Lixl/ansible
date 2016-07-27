package com.ansible.www;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/11.
 */
@WebServlet(name = "AnsibleTest",urlPatterns="/AnsibleTest")
public class AnsibleTest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String myhost = request.getParameter("myhost");
        String mymodule = request.getParameter("mymodule");
        String mycommand = request.getParameter("mycommand");
        String ansiblecommand = null;
        if (mycommand != null && !"".equals(mycommand)){
            ansiblecommand = "ansible "+myhost+" -m "+mymodule+" -a \""+mycommand+"\"";
        }
        if (ansiblecommand == null || "".equals(ansiblecommand)){
            request.getRequestDispatcher("/html/myansible.jsp").forward(request, response);
            return;
        }
        List<String> processList = new ArrayList<String>();
        String[] cmd = new String[]{"/bin/sh", "-c", ansiblecommand};
        Process process = Runtime.getRuntime().exec(cmd);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            processList.add(line);
        }
        bufferedReader.close();
        request.setAttribute("processList", processList);
        request.setAttribute("ansiblecommand",ansiblecommand);
        request.getRequestDispatcher("/html/myansible.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
