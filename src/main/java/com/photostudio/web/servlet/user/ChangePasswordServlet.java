package com.photostudio.web.servlet.user;

import com.photostudio.ServiceLocator;
import com.photostudio.entity.user.User;
import com.photostudio.security.SecurityService;
import com.photostudio.security.entity.Session;
import com.photostudio.service.UserService;
import com.photostudio.web.templater.TemplateEngineFactory;
import com.photostudio.web.util.CommonVariableAppendService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/security/change-password")
@Slf4j
public class ChangePasswordServlet extends HttpServlet {
    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);
    private UserService userService = ServiceLocator.getService(UserService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.info("Change password form received");

        Map<String, Object> paramsMap = new HashMap<>();
        CommonVariableAppendService.appendUser(paramsMap, request);

        response.setContentType("text/html;charset=utf-8");
        TemplateEngineFactory.process(request, response, "change-password", paramsMap);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Request with change password received");

        Map<String, Object> paramsMap = new HashMap<>();
        CommonVariableAppendService.appendUser(paramsMap, request);

        Session session = (Session) request.getAttribute("session");
        User user = session.getUser();

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String repeatNewPassword = request.getParameter("repeatNewPassword");

        if (securityService.isOldPassword(oldPassword, user)) {
            if (newPassword.equals(repeatNewPassword)) {
                userService.changeUserPassword(user, newPassword);
                response.sendRedirect(request.getContextPath() + "/user?id=" + user.getId());
            } else {
                paramsMap.put("invalid", "notMatchPassword");
                TemplateEngineFactory.process(request, response, "change-password", paramsMap);
            }
        } else {
            paramsMap.put("invalid", "incorrectPassword");
            TemplateEngineFactory.process(request, response, "change-password", paramsMap);
        }
    }
}
