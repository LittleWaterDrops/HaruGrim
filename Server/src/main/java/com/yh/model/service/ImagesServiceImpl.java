package com.yh.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.model.dao.ImagesDao;
import com.yh.model.dto.Images;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesDao imagesDao;

    @Override
    public Images getImageById(long id) {
        return imagesDao.findById(id);
    }

    @Override
    public List<Images> getImagesByReviewId(long reviewId) {
        return imagesDao.findByReviewId(reviewId);
    }

    @Override
    public Images saveImage(Images image) {
        imagesDao.saveImage(image);
        return image;
    }

    @Override
    public void deleteImage(long id) {
        imagesDao.deleteImage(id);
    }
}

