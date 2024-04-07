package com.jnb.animaldoctor24.domain.hospital.application.impl;

import com.jnb.animaldoctor24.domain.hospital.application.LikeService;
import com.jnb.animaldoctor24.domain.hospital.dao.LikeRepo;
import com.jnb.animaldoctor24.domain.hospital.domain.Like;
import com.jnb.animaldoctor24.domain.hospital.dto.LikeDeleteRequest;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.global.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(LikeServiceImpl.class);
    private final LikeRepo likeRepository;

    @Override
    public ResponseEntity<String> add(Long hn, String email) {
        Optional<Like> like = likeRepository.findByHnAndEmail(hn, email);
        if(like.isPresent()) {
            likeRepository.delete(like.get());
            return Utils.getResponseEntity(ResponseConstants.LIKE_ALREADY_EXIST_DELETE_SUCCESS, HttpStatus.OK);
        }

        likeRepository.save(getLikeFromRequest(hn, email));
        return Utils.getResponseEntity(ResponseConstants.LIKE_ADD_SUCCESS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(Long ln, LikeDeleteRequest request) {
        Optional<Like> like = likeRepository.findByLnAndEmail(ln, request.getEamil());
        if(like.isEmpty()) {
            likeRepository.save(getLikeFromRequest(request.getHn(), request.getEamil()));
            return Utils.getResponseEntity(ResponseConstants.LIKE_DOES_NOT_EXIST_ADD_SUCCESS, HttpStatus.OK);
        }

        likeRepository.delete(like.get());
        return Utils.getResponseEntity(ResponseConstants.LIKE_DELETE_SUCCESS, HttpStatus.OK);
    }

    private Like getLikeFromRequest(Long hn, String email) {
        Like like = new Like();
        like.setHn(hn);
        like.setEmail(email);
        return like;
    }
}
