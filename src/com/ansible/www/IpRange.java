package com.ansible.www;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/18.
 */
@WebServlet(name = "IpRange")
public class IpRange extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String type = request.getParameter("type");
        if (type != null && "diff".equals(type)) {
            String srcip = request.getParameter("srcip");
            ArrayList<String> l_src = new ArrayList<>();
            if (srcip != null && !"".equals(srcip)) {
                String[] lines = srcip.split("\r\n");
                for (int i = 0; i < lines.length; i++) {
                    if (!"".equals(lines[i]) && lines[i] != null) {
                        l_src.add(lines[i]);
                    }
                }
            }
            ArrayList<String> l_dst = new ArrayList<>();
            String dstip = request.getParameter("dstip");
            if (dstip != null && !"".equals(dstip)) {
                String[] lines = dstip.split("\r\n");
                for (int i = 0; i < lines.length; i++) {
                    if (!"".equals(lines[i]) && lines[i] != null) {
                        l_dst.add(lines[i]);
                    }
                }
            }
            ArrayList<String> result = new ArrayList<>();
            IpInRange ipInRange = new IpInRange();
            for (String ip1 : l_src) {
                for (String ip2 : l_dst) {
                    boolean bool = ipInRange.isInRange(ip1, ip2);
                    if (bool == true) {
                        result.add(ip1 + "\tin\t" + ip2);
                    } else {
                        result.add(ip1 + "\tout\t" + ip2);
                    }
                }
            }
            request.setAttribute("result", result);
            request.getRequestDispatcher("/WEB-INF/other/iprange.jsp").forward(request, response);
        }else if (type != null && "qucong".equals(type)){
            IpInRange ipInRange = new IpInRange();
            String srcip = request.getParameter("srcip");
            ArrayList<String> l_src = new ArrayList<>();
            if (srcip != null && !"".equals(srcip)) {
                String[] lines = srcip.split("\r\n");
                for (int i = 0; i < lines.length; i++) {
                    if (!"".equals(lines[i]) && lines[i] != null) {
                        l_src.add(lines[i]);
                    }
                }
            }
            ArrayList<String> result = new ArrayList<>();
            for (int i = 0; i < l_src.size(); i++){
                boolean bool = false;
                String myip = l_src.get(i);
                for (int j = 0; j < l_src.size(); j++){
                    if (i != j){
                        String mydstip = l_src.get(j);
                        if (ipInRange.isInRange(myip, mydstip)){
                            if (ipInRange.isSame(myip,mydstip)){
                                l_src.set(i,"");
                            }
                            bool = true;
                            break;
                        }
                    }
                }
                if (false == bool){
                    result.add(myip);
                }
            }
            request.setAttribute("result", result);
            request.getRequestDispatcher("/WEB-INF/other/iprange.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
