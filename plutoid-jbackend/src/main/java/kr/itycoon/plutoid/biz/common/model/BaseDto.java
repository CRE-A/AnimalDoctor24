package kr.itycoon.plutoid.biz.common.model;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
