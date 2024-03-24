package com.jnb.animaldoctor24.domain.hospital.application.impl;

import com.jnb.animaldoctor24.domain.hospital.api.HospitalRequest;
import com.jnb.animaldoctor24.domain.hospital.api.ReviewRequest;
import com.jnb.animaldoctor24.domain.hospital.application.HospitalService;
import com.jnb.animaldoctor24.domain.hospital.constants.ResponseConstants;
import com.jnb.animaldoctor24.domain.hospital.dao.HospitalRepo;
import com.jnb.animaldoctor24.domain.hospital.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    @Override
    public ResponseEntity<String> register(HospitalRequest request) {
        try{
            return Utils.getResponseEntity(ResponseConstants.HOSPITAL_REGISTER_SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return Utils.getResponseEntity(ResponseConstants.HOSPITAL_REGISTER_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> modify(HospitalRequest request) {
        try{
            return Utils.getResponseEntity(ResponseConstants.HOSPITAL_MODIFY_SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return Utils.getResponseEntity(ResponseConstants.HOSPITAL_MODIFY_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> delete(HospitalRequest request) {
        try {
            return Utils.getResponseEntity(ResponseConstants.HOSPITAL_DELETE_SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return Utils.getResponseEntity(ResponseConstants.HOSPITAL_DELETE_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

}
