package com.ansible.www;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Administrator on 2015/8/9.
 */
@WebServlet(name = "CommClServlet",urlPatterns="/CommClServlet")
public class CommClServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.println("第一个窗口是共有的，第二窗口是第一段文本独有的，第三个窗口是第二段文本独有的。<br />");
        String text1  = request.getParameter("text1");
        String text2 = request.getParameter("text2");
        String path1 = this.getServletContext().getRealPath("/tmp4");
        String path2 = this.getServletContext().getRealPath("/tmp5");
        BufferedReader bufferedReader = null;
        OutputStreamWriter outputStreamWriter1 = null;
        BufferedWriter bufferedWriter1 = null;
        OutputStreamWriter outputStreamWriter2 = null;
        BufferedWriter bufferedWriter2 = null;
        Process process = null;
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
            String cmd1 = "/usr/bin/comm "+path1+" "+path2+" -1 -2";
            process = Runtime.getRuntime().exec(cmd1);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line1 = "";
            out.println("<textarea rows=\"30\" cols=\"50\">");
            while ((line1 = bufferedReader.readLine()) != null) {
                out.println(line1);
            }
            out.println("</textarea>");

            String cmd2 = "/usr/bin/comm "+path1+" "+path2+" -2 -3";
            process = Runtime.getRuntime().exec(cmd2);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line2 = "";
            out.println("<textarea rows=\"30\" cols=\"50\">");
            while ((line2 = bufferedReader.readLine()) != null) {
                out.println(line2);
            }
            out.println("</textarea>");

            String cmd3 = "/usr/bin/comm "+path1+" "+path2+" -1 -3";
            process = Runtime.getRuntime().exec(cmd3);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line3 = "";
            out.println("<textarea rows=\"30\" cols=\"50\">");
            while ((line3 = bufferedReader.readLine()) != null) {
                out.println(line3);
            }
            out.println("</textarea>");
        }catch (Exception e){
            System.out.println(e);
        }finally {
            bufferedReader.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
