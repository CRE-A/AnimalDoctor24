package kr.itycoon.plutoid.global.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.itycoon.plutoid.biz.member.domain.Member;
import kr.itycoon.plutoid.biz.member.service.MemberService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberService memberService;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.findMemberByEmail(username);
        if (member == null) {
            throw new UsernameNotFoundException("사용자가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
        return new CustomUserDetails(
            member.getMemberId(),
            member.getEmail(),
            member.getPassword(),
            member.getMemberRole(),
            member.getActiveYn());
   }

}