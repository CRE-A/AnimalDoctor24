package com.jnb.animaldoctor24.domain.hospital.application.impl;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRegisterRequest;
import com.jnb.animaldoctor24.domain.hospital.application.HospitalService;
import com.jnb.animaldoctor24.domain.hospital.error.exception.HospitalDeleteException;
import com.jnb.animaldoctor24.domain.hospital.error.exception.HospitalModifyException;
import com.jnb.animaldoctor24.domain.hospital.error.exception.HospitalRegisterException;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.domain.hospital.dao.HospitalRepo;
import com.jnb.animaldoctor24.global.error.exception.DataAlreadyExistException;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import com.jnb.animaldoctor24.global.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(HospitalServiceImpl.class);
    private final HospitalRepo hospitalRepository;
    private final EntityManager em;

    @Override
    public List<Hospital> list() throws RuntimeException{
        List<Hospital> listOfHospital = hospitalRepository.findAll();
        if(listOfHospital==null || listOfHospital.isEmpty()){
            throw new DataNotFoundException(ResponseConstants.HOSPITAL_DOES_NOT_EXISTS);
        }

        return listOfHospital;
    }

    @Override
    public Hospital getHospital(Integer hn) throws RuntimeException{
        Optional<Hospital> hospitalInfo = hospitalRepository.findByHn(hn);
        if(hospitalInfo.isEmpty()){
            throw new DataNotFoundException(ResponseConstants.HOSPITAL_DOES_NOT_EXISTS);
        }

        return hospitalInfo.get();
    }

    @Override
    public ResponseEntity<String> register(HospitalRegisterRequest request) throws HospitalRegisterException {
        Optional<Hospital> hospital = hospitalRepository.findHospitalByHospitalName(request.getHospitalName());
        if(hospital.isPresent()) {
            throw new DataAlreadyExistException(ResponseConstants.HOSPITAL_ALREADY_EXISTS);
        }

        hospitalRepository.save(getHospitalFromRequest(request));
        return Utils.getResponseEntity(ResponseConstants.HOSPITAL_REGISTER_SUCCESS, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> modify(HospitalModifyRequest request, Integer hn) throws HospitalModifyException {
        Optional<Hospital> hospitalInfo = hospitalRepository.findByHn(hn);
        if(hospitalInfo.isEmpty()) {
            throw new DataNotFoundException(ResponseConstants.HOSPITAL_DOES_NOT_EXISTS);
        }

        updateHospitalInfo(request, hn);
        return Utils.getResponseEntity(ResponseConstants.HOSPITAL_MODIFY_SUCCESS, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> delete(Integer hn) throws HospitalDeleteException {
        Optional<Hospital> hospitalInfo = hospitalRepository.findByHn(hn);
        if(hospitalInfo.isEmpty()) {
            throw new DataNotFoundException(ResponseConstants.HOSPITAL_DOES_NOT_EXISTS);
        }

        hospitalRepository.delete(hospitalInfo.get());
        return Utils.getResponseEntity(ResponseConstants.HOSPITAL_DELETE_SUCCESS, HttpStatus.OK);
    }


    private Hospital getHospitalFromRequest(HospitalRegisterRequest request) throws RuntimeException{
        Hospital hospital = new Hospital();
        hospital.setEmail(request.getEmail());
        hospital.setRole(request.getRole());
        hospital.setHospitalName(request.getHospitalName());
        hospital.setLocation(request.getLocation());
        hospital.setDescription(request.getDescription());
        hospital.setBusinessDay(request.getBusinessDay());
        hospital.setBusinessHour(request.getBusinessHour());
        hospital.setLunchHour(request.getLunchHour());
        hospital.setHospitalPhoneNumber(request.getHospitalPhoneNumber());
        hospital.setImagePath(request.getImagePath());
        return hospital;
    }

    private void updateHospitalInfo(HospitalModifyRequest request, Integer hn) throws RuntimeException{
        Hospital hospital = em.find(Hospital.class, hn);

        hospital.setEmail(request.getEmail());
        hospital.setRole(request.getRole());
        hospital.setHospitalName(request.getHospitalName());
        hospital.setLocation(request.getLocation());
        hospital.setDescription(request.getDescription());
        hospital.setBusinessDay(request.getBusinessDay());
        hospital.setBusinessHour(request.getBusinessHour());
        hospital.setLunchHour(request.getLunchHour());
        hospital.setHospitalPhoneNumber(request.getHospitalPhoneNumber());
        hospital.setImagePath(request.getImagePath());
    }

}
