package com.jnb.animaldoctor24.domain.member.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BaseDto {
  /**
   * 생성자
   */
  @JsonIgnore  
  private  String createBy;
  
  /**
   * 생성일시
   */
  @JsonIgnore
  private  Date  createDate;
  
  /**
   * 수정자
   */
  @JsonIgnore
  private  String modifyBy ;
  
  /**
   * 수정일시
   */
  @JsonIgnore
  private  Date  modifyDate ;


}
