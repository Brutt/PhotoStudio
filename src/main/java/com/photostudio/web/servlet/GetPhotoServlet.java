package com.photostudio.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class GetPhotoServlet extends HttpServlet {
    private static final int BUFFER_SIZE = 8192;
    private static final String PATH_TO_PHOTO = "/photo";
    private final Logger LOG = LoggerFactory.getLogger(getClass());


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("Request to photo source received");

        String prefixPath = request.getContextPath() + PATH_TO_PHOTO;

        String photoPath = request.getRequestURI().substring(prefixPath.length());

        try (InputStream resourceAsStream = new FileInputStream(photoPath);
             BufferedInputStream styleStream = new BufferedInputStream(resourceAsStream)) {

            ServletOutputStream outputStream = response.getOutputStream();

            int count;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((count = styleStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, count);
            }
        } catch (FileNotFoundException e) {
            LOG.error("Photo not found by path {}", photoPath, e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            LOG.error("Loading photo by path {} error", photoPath, e);
            throw new RuntimeException("Loading photo by path: " + photoPath + "error", e);
        }
    }
}