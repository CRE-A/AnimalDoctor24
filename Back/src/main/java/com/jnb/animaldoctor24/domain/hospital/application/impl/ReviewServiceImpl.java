package com.jnb.animaldoctor24.domain.hospital.application.impl;

import com.jnb.animaldoctor24.domain.hospital.domain.Review;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.ReviewRegisterRequest;
import com.jnb.animaldoctor24.domain.hospital.application.ReviewService;
import com.jnb.animaldoctor24.domain.hospital.dao.HospitalRepo;
import com.jnb.animaldoctor24.domain.hospital.dao.ReviewRepo;
import com.jnb.animaldoctor24.domain.hospital.error.exception.ReviewDeleteException;
import com.jnb.animaldoctor24.domain.hospital.error.exception.ReviewRegisterException;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.global.error.exception.DataAlreadyExistException;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import com.jnb.animaldoctor24.global.util.Utils;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepo reviewRepository;
    private final EntityManager em;



    @Override
    public List<Review> list(Integer hn) throws RuntimeException{
        List<Review> listOfReview = reviewRepository.findAllByHn(hn);
        if(listOfReview==null || listOfReview.isEmpty()){
            throw new DataNotFoundException(ResponseConstants.REVIEW_DOES_NOT_EXISTS);
        }

        return listOfReview;
    }

    @Override
    public Review getReview(Integer rn) throws RuntimeException{
        Optional<Review> review = reviewRepository.findByRn(rn);
        if(review.isEmpty()){
            throw new DataNotFoundException(ResponseConstants.REVIEW_DOES_NOT_EXISTS);
        }
        return review.get();
    }

    @Override
    public ResponseEntity<String> register(ReviewRegisterRequest request, Integer hn) throws ReviewRegisterException {
        reviewRepository.save(getReviewFromRequest(request,hn));
        return Utils.getResponseEntity(ResponseConstants.REVIEW_REGISTER_SUCCESS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> modify(HospitalModifyRequest request, Integer rn) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Integer rn) throws ReviewDeleteException {
        Optional<Review> reviewInfo = reviewRepository.findByRn(rn);
        if(reviewInfo.isEmpty()){
            throw new DataAlreadyExistException(ResponseConstants.REVIEW_DOES_NOT_EXISTS);
        }
        reviewRepository.delete(reviewInfo.get());
        return Utils.getResponseEntity(ResponseConstants.REVIEW_DELETE_SUCCESS, HttpStatus.OK);
    }

    private Review getReviewFromRequest(ReviewRegisterRequest request, Integer hn){
        Review review= new Review();
        review.setHn(hn);
        review.setEmail(request.getEmail());
        review.setRole(request.getRole());
        review.setTitle(request.getTitle());
        review.setContents(request.getContents());
        review.setImagePath(request.getImagePath());
        return review;
    }
}
