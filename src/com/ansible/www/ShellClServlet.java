package com.ansible.www;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/6.
 */
@WebServlet(name = "ShellClServlet",urlPatterns="/ShellClServlet")
public class ShellClServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.println("欢迎使用对比，符号的含义如下：1: + 代表增加的行  2: - 代表删除的行 <br />");
        String text1  = request.getParameter("text1");
        String text2 = request.getParameter("text2");
        String path1 = this.getServletContext().getRealPath("/tmp1");
        String path2 = this.getServletContext().getRealPath("/tmp2");
        BufferedReader bufferedReader = null;
        OutputStreamWriter outputStreamWriter1 = null;
        BufferedWriter bufferedWriter1 = null;
        OutputStreamWriter outputStreamWriter2 = null;
        BufferedWriter bufferedWriter2 = null;
        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            outputStreamWriter1 = new OutputStreamWriter(new FileOutputStream(path1),"utf-8");
            bufferedWriter1 = new BufferedWriter(outputStreamWriter1);
            String[] lines = text1.split("\r\n");
            for(int i=0; i<lines.length;i++) {
                bufferedWriter1.write(lines[i]);
                bufferedWriter1.newLine();
            }
            bufferedWriter1.close();
            outputStreamWriter1.close();
            outputStreamWriter2 = new OutputStreamWriter(new FileOutputStream(path2),"utf-8");
            bufferedWriter2 = new BufferedWriter(outputStreamWriter2);
            lines = text2.split("\r\n");
            for(int i=0; i<lines.length;i++) {
                bufferedWriter2.write(lines[i]);
                bufferedWriter2.newLine();
            }
            bufferedWriter2.close();
            outputStreamWriter2.close();
            String cmd = "/usr/bin/diff -u "+path1+" "+path2;
            process = Runtime.getRuntime().exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                processList.add(line);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        out.println("<textarea rows=\"30\" cols=\"150\" wrap=\"soft\">");
        for (String line: processList){
            out.println(line);
        }
        out.println("</textarea>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
