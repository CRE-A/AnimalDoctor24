package com.jnb.animaldoctor24.domain.member.api;

import com.jnb.animaldoctor24.domain.member.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
  private String contactNumber;
    private Role role;


}
