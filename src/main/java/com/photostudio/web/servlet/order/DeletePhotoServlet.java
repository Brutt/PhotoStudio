package com.photostudio.web.servlet.order;

import com.photostudio.ServiceLocator;
import com.photostudio.service.OrderService;
import com.photostudio.web.util.UtilClass;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/order/delete-photos/*")
@Slf4j
public class DeletePhotoServlet extends HttpServlet {
    private OrderService orderService = ServiceLocator.getService(OrderService.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String uri = request.getRequestURI();
        int orderId = UtilClass.getIdFromPath(uri);

        long photoId = Long.parseLong(request.getParameter("photoId"));
        log.info("Request delete photo is received: photo {}", orderId);

        try {
            if (photoId == 0) {
                orderService.deletePhotos(orderId);
            } else {
                orderService.deletePhoto(orderId, photoId);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect(request.getContextPath() + "/order/" + orderId);
        } catch (Exception e) {
            log.error("Error in the request for delete order", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException("Error trying to delete order", e);
        }
    }
}
