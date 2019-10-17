package com.tenxun.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;



import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * 录音文件识别
 */
@WebServlet("/speechServlet")
@MultipartConfig
public class SpeechServlet extends BaseServlet {
    /**
     * 录音然后上传文件
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void speech(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //处理post请求中文乱码问题
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(900);//单位为秒
        //获取文件上传的路径
        String uploadPath = getServletContext().getRealPath("/upload");
        Part part = request.getPart("audioData");
        String path="";
        //获取uuid
        String replace = UUID.randomUUID().toString().replace("-", "");
        //设置uuid为文件名
        String fileName =replace+".wav";
                path=uploadPath+File.separator+fileName;
            part.write(path);

        //3、响应数据
        //处理响应乱码
        response.setContentType("text/html;charset=utf-8");


    }

}

