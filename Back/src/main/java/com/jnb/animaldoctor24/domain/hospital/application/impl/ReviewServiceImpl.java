package com.jnb.animaldoctor24.domain.hospital.application.impl;

import com.jnb.animaldoctor24.domain.hospital.dto.ReviewRequest;
import com.jnb.animaldoctor24.domain.hospital.application.ReviewService;
import com.jnb.animaldoctor24.domain.hospital.dao.HospitalRepo;
import com.jnb.animaldoctor24.domain.hospital.dao.ReviewRepo;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.global.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;
    private final HospitalRepo hospitalRepo;


    @Override
    public ResponseEntity<String> register(ReviewRequest request) {
        try{
            return Utils.getResponseEntity(ResponseConstants.REVIEW_REGISTER_SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return Utils.getResponseEntity(ResponseConstants.REVIEW_REGISTER_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> modify(ReviewRequest request) {
        try{
            return Utils.getResponseEntity(ResponseConstants.REVIEW_MODIFY_SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return Utils.getResponseEntity(ResponseConstants.REVIEW_MODIFY_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> delete(ReviewRequest request) {
        try{
            return Utils.getResponseEntity(ResponseConstants.REVIEW_DELETE_SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return Utils.getResponseEntity(ResponseConstants.REVIEW_DELETE_FAILED, HttpStatus.BAD_REQUEST);

        }
    }
}
