package com.jnb.animaldoctor24.domain.hospital.application.impl;

import com.jnb.animaldoctor24.domain.hospital.domain.Review;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.ReviewModifyRequest;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepo reviewRepository;
    private final EntityManager em;



    @Override
    public List<Review> list(Long hn) throws RuntimeException{
        List<Review> listOfReview = reviewRepository.findAllByHn(hn);
        if(listOfReview==null || listOfReview.isEmpty()){
            throw new DataNotFoundException(ResponseConstants.REVIEW_DOES_NOT_EXISTS);
        }

        return listOfReview;
    }

    @Override
    public Review getReview(Long rn) throws RuntimeException{
        Optional<Review> review = reviewRepository.findByRn(rn);
        if(review.isEmpty()){
            throw new DataNotFoundException(ResponseConstants.REVIEW_DOES_NOT_EXISTS);
        }
        return review.get();
    }

    @Override
    public ResponseEntity<String> register(ReviewRegisterRequest request, Long hn) throws ReviewRegisterException {
        reviewRepository.save(getReviewFromRequest(request,hn));
        return Utils.getResponseEntity(ResponseConstants.REVIEW_REGISTER_SUCCESS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> modify(ReviewModifyRequest request, Long rn) {
        Optional<Review> review = reviewRepository.findByRn(rn);
        if(review.isEmpty()) {
            throw new DataNotFoundException(ResponseConstants.REVIEW_DOES_NOT_EXISTS);
        }

        updateReview(request, rn);
        return Utils.getResponseEntity(ResponseConstants.REVIEW_MODIFY_SUCCESS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(Long rn) throws ReviewDeleteException {
        Optional<Review> reviewInfo = reviewRepository.findByRn(rn);
        if(reviewInfo.isEmpty()){
            throw new DataAlreadyExistException(ResponseConstants.REVIEW_DOES_NOT_EXISTS);
        }
        reviewRepository.delete(reviewInfo.get());
        return Utils.getResponseEntity(ResponseConstants.REVIEW_DELETE_SUCCESS, HttpStatus.OK);
    }

    private Review getReviewFromRequest(ReviewRegisterRequest request, Long hn){
        Review review= new Review();

        review.setHn(hn);
        review.setEmail(request.getEmail());
        review.setRole(request.getRole());
        review.setTitle(request.getTitle());
        review.setContents(request.getContents());
        review.setImagePath(request.getImagePath());
        return review;
    }

    private void updateReview(ReviewModifyRequest request, Long rn) {
        Review review = em.find(Review.class, rn);

        review.setTitle(request.getTitle());
        review.setContents(request.getContents());
        review.setImagePath(request.getImagePath());

    }
}
