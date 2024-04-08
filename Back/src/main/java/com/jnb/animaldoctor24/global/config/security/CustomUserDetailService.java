package com.jnb.animaldoctor24.global.config.security;

import com.jnb.animaldoctor24.domain.member.application.MemberService;
import com.jnb.animaldoctor24.domain.member.dao.MemberRepo;
import com.jnb.animaldoctor24.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

//    private final MemberService memberService;
    private final MemberRepo memberRepo;


    @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepo.findByEmail(username);
        if(member.isEmpty()){
            throw new UsernameNotFoundException("사용자가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }

        return new CustomUserDetails(
            member.get().getEmail(),
            member.get().getPassword(),
            member.get().getRole(),
            member.get().getActiveYn());
   }

}