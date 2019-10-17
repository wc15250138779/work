package com.tenxun.test;


import com.tencentcloudapi.asr.v20190614.models.DescribeTaskStatusRequest;
import com.tencentcloudapi.asr.v20190614.models.DescribeTaskStatusResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.asr.v20190614.AsrClient;

import com.tencentcloudapi.asr.v20190614.models.CreateRecTaskRequest;
import com.tencentcloudapi.asr.v20190614.models.CreateRecTaskResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 腾讯ASR
 */
@WebServlet("/speechRecognition")
public class SpeechRecognition extends BaseServlet {

    /**
     * 语音识别任务
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void speechTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //1.接受请求
        //处理post请求中文乱码问题
        request.setCharacterEncoding("UTF-8");

        //2.处理请求
        //获取文件名
        String fileName = request.getParameter("fileName");
        if(fileName!=null){
            String URl = "https://www.enablue.com/upload/"+fileName;
            System.out.println("URl = " + URl);
            String result = speechByURL(URl);
            //3.响应数据
            //处理响应乱码
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(result);
        }

    }

    /**
     * 查询结果
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void result(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //1.接受请求
        //处理post请求中文乱码问题
        request.setCharacterEncoding("UTF-8");
        String taskid = request.getParameter("taskid");
        String result = null;
        //2.处理请求
        try {
            //3.响应数据
            //处理响应乱码
            response.setContentType("text/html;charset=utf-8");
            result = queryResult(Integer.parseInt(taskid));

        }catch (Exception e){
            e.printStackTrace();

        }
        response.getWriter().println(result);


    }

    /**
     * 录音文件识别
     */
    public String speechByURL(String url) {
        try{

            Credential cred = new Credential("AKID82lDUV6qa6LJ2VdiRtTn10iAMIxR54D9", "oMhnkfMmulLgkX8VaOZT5SZREl15rMaX");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("asr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            AsrClient client = new AsrClient(cred, "ap-shanghai", clientProfile);

            String params = "{\"EngineModelType\":\"16k_0\",\"ChannelNum\":1,\"ResTextFormat\":0,\"SourceType\":0,\"Url\":\""+url+"\"}";
            CreateRecTaskRequest req = CreateRecTaskRequest.fromJsonString(params, CreateRecTaskRequest.class);

            CreateRecTaskResponse resp = client.CreateRecTask(req);
            String result = CreateRecTaskRequest.toJsonString(resp);
            System.out.println("result = " + result);

            return result;

        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * 识别结果查询
     */
    public  String queryResult(int taskId) {
        try{
            Credential cred = new Credential("AKID82lDUV6qa6LJ2VdiRtTn10iAMIxR54D9", "oMhnkfMmulLgkX8VaOZT5SZREl15rMaX");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("asr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            AsrClient client = new AsrClient(cred, "ap-shanghai", clientProfile);

            String params = "{\"TaskId\":"+taskId+"}";
            DescribeTaskStatusRequest req = DescribeTaskStatusRequest.fromJsonString(params, DescribeTaskStatusRequest.class);

            DescribeTaskStatusResponse resp = client.DescribeTaskStatus(req);

            String result = DescribeTaskStatusRequest.toJsonString(resp);

            System.out.println("result = " + result);
            return result;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }

    }

}