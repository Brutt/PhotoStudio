package com.photostudio.web.servlet;

import com.photostudio.web.templater.TemplateEngineFactory;
import com.photostudio.web.util.CommonVariableAppendService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/access-denied")
public class AccessDeniedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
            Map<String, Object> paramsMap = new HashMap<>();

            CommonVariableAppendService.appendUser(paramsMap, request);
            response.setContentType("text/html;charset=utf-8");

            TemplateEngineFactory.process(request, response, "access_denied", paramsMap);
    }
}
