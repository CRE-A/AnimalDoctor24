package kr.itycoon.plutoid.biz.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {

    private String memberId;

    private String memberName;

    private String photoUrl;

    private String accessToken;

}
