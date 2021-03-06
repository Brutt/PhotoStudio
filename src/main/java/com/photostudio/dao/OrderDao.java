package com.photostudio.dao;

import com.photostudio.entity.order.FilterParameters;
import com.photostudio.entity.order.Order;
import com.photostudio.entity.order.OrderStatus;
import com.photostudio.entity.photo.Photo;
import com.photostudio.entity.photo.PhotoStatus;

import java.util.List;

public interface OrderDao {
    List<Order> getAll();

    List<Order> getOrdersByParameters(FilterParameters filterParameters);

    List<Order> getOrdersByUserId(long userId);

    Order getOrderById(int id);

    int add(Order order, int orderStatusId);

    void editOrderByAdmin(int orderId, long userId, String commentAdmin);

    void editOrderByUser(int orderId, String commentUser);

    void savePhotos(int orderId, List<String> photosPath);

    void updateStatusRetouchedPhotos(List<String> photosPath, int orderId);

    void selectPhotos(int orderId, String selectedPhotos);

    void changeOrderStatus(int id, int orderStatusId);

    OrderStatus getOrderStatus(int id);

    int getPhotoCount(int id);

    int getPhotoCountByStatus(int id, int idPhotoStatus);

    String getPathByPhotoId(long photoId);

    void deleteOrdersByUserId(List<Order> orderList, long id);

    List<Photo> getPhotosByStatus (int orderId, PhotoStatus photoStatus);

    void delete(int id);

    void deletePhoto(long photoId);

    void deletePhotos(int orderId);

    List<String> getSelectedPhotosSourcesByOrderId(int orderId);
}
