package com.jnb.animaldoctor24.domain.hospital.application.impl;

import com.jnb.animaldoctor24.domain.hospital.application.LikeService;
import com.jnb.animaldoctor24.domain.hospital.dao.LikeRepo;
import com.jnb.animaldoctor24.domain.hospital.domain.Like;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import com.jnb.animaldoctor24.global.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(LikeServiceImpl.class);
    private final LikeRepo likeRepository;

    @Override
    public ResponseEntity<String> add(Integer hn, String email) {
        Optional<Like> like = likeRepository.findByHnAndEmail(hn, email);
        if(like.isPresent()) {
            delete(like.get().getLn(), email);
        }

        likeRepository.save(getLikeFromRequest(hn, email));
        return Utils.getResponseEntity(ResponseConstants.LIKE_ADD_SUCCESS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(Integer ln, String email) {
        Optional<Like> like = likeRepository.findByLnAndEmail(ln, email);
        if(like.isEmpty()) {
            add(like.get().getHn(), email);
        }

        likeRepository.delete(like.get());
        return Utils.getResponseEntity(ResponseConstants.LIKE_DELETE_SUCCESS, HttpStatus.OK);
    }

    private Like getLikeFromRequest(Integer hn, String email) {
        Like like = new Like();
        like.setHn(hn);
        like.setEmail(email);
        return like;
    }
}
