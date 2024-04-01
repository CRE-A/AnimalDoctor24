package com.jnb.animaldoctor24.domain.hospital.application.impl;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalResponse;
import com.jnb.animaldoctor24.domain.hospital.application.HospitalService;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.domain.hospital.dao.HospitalRepo;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import com.jnb.animaldoctor24.global.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepo hospitalRepository;

    @Override
    public List<Hospital> list() {
        List<Hospital> listOfHospital = hospitalRepository.findAllBy();
        if(listOfHospital==null){
            throw new DataNotFoundException(ResponseConstants.HOSPITAL_DOES_NOT_EXISTS);
        }
        return listOfHospital;
    }

    @Override
    public Hospital getHospital(Integer hn) {
        Hospital hospitalInfo = hospitalRepository.findByHn(hn);
        if(hospitalInfo==null){
            throw new DataNotFoundException(ResponseConstants.HOSPITAL_DOES_NOT_EXISTS);
        }
        return hospitalInfo;
    }

    @Override
    public ResponseEntity<String> register(HospitalRequest request) {
        try{
            hospitalRepository.save(getHospitalFromRequest(request));
            return Utils.getResponseEntity(ResponseConstants.HOSPITAL_REGISTER_SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return Utils.getResponseEntity(ResponseConstants.HOSPITAL_REGISTER_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> modify(HospitalRequest request) {
        try{
//            hospitalRepository.;
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


    private Hospital getHospitalFromRequest(HospitalRequest request){
        Hospital hospital = new Hospital();
        hospital.setEmail(request.getEmail());
        hospital.setRole(request.getRole());
        hospital.setName(request.getName());
        hospital.setContents(request.getContents());
        hospital.setTag(request.getTag());
        hospital.setImgPath(request.getImgPath());
        return hospital;
    }

}
