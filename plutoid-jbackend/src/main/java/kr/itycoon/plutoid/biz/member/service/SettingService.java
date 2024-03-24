package kr.itycoon.plutoid.biz.member.service;

import kr.itycoon.plutoid.biz.member.domain.Setting;
import kr.itycoon.plutoid.biz.member.mapper.SettingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SettingService {

    private final SettingMapper settingMapper;


    // key 순서 채번
    public Integer nextSettingNo(String memberId) {
        Integer maxSettingNo = settingMapper.getMaxSettingNo(memberId);
        if (maxSettingNo == null || maxSettingNo.equals(null)) {
            return 1;
        } else {
            return maxSettingNo + 1;
        }
    }

    // 설정 생성

    public int createSetting(Setting setting) {
        return settingMapper.insertSetting(setting);
    }

    // 마지막 설정 조회

}
