package kr.itycoon.plutoid.biz.member.mapper;

import kr.itycoon.plutoid.biz.member.domain.Setting;
import kr.itycoon.plutoid.biz.member.domain.SettingDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SettingMapper {

    Setting findSettingByKey(String memberId);

    Integer insertSetting(Setting setting);

    Integer insertSettingDetails(SettingDetail settingDetail);

    Integer getMaxSettingNo(String memberId);

    List<SettingDetail> selectSettingDetails(String memberId);

}
