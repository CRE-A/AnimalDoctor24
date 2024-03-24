package kr.itycoon.plutoid;

import kr.itycoon.plutoid.biz.llm.domain.LLMModel;
import kr.itycoon.plutoid.biz.llm.service.LLMService;
import kr.itycoon.plutoid.biz.member.domain.Setting;
import kr.itycoon.plutoid.biz.member.domain.SettingDetail;
import kr.itycoon.plutoid.biz.member.mapper.SettingMapper;
import kr.itycoon.plutoid.biz.member.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class SettingMapperTest {

    @Autowired
    SettingMapper settingMapper;

    @Autowired
    SettingService settingService;

    @Autowired
    LLMService llmService;

    @Test
    public void nextSettingNoTest() {
        Integer settingNo = settingService.nextSettingNo("9dbe75e3-b49b-4f3e-83df-8aaa85e6b95a");
        log.debug("settingNo : {}", settingNo);
    }

    @Test
    public void getListTest() {
        List<SettingDetail> list = settingMapper.selectSettingDetails("990ac290-584a-4731-9a82-33a2d2aed0c9");
        log.debug("list : {}", list);
        assertTrue(list.size() == 5);
    }

    @Test
    public void createDefaultLlmSettingTest() {
        // 현재 LLM공급자는 OPEN_AI만 가능

        // 기본 모델 조회
        LLMModel model = llmService.findDefaultLlmModel("OPEN_AI");
        log.debug("기본 모델 조회 LLMModel : ", model);

        // 설정 저장
        Setting setting = Setting.builder()
                                 .memberId("9dbe75e3-b49b-4f3e-83df-8aaa85e6b95a")
                                 .settingNo(settingService.nextSettingNo("9dbe75e3-b49b-4f3e-83df-8aaa85e6b95a"))
                                 .llmProvider("OPEN_AI")
                                 .llmModel(model.getLlmModel())
                                 .settingDate(new Date())
                                 .defaultValueYn("Y")
                                 .build();
        log.debug("setting : {}", setting);
        int cnt = settingMapper.insertSetting(setting);
        log.debug("설정저장 setting : ", setting);

        assertTrue(cnt == 1);

        // 기본 파라미터 값
        Map<String, String> defaults = new HashMap<>();
        defaults.put("temperature", "1");
        defaults.put("max_tokens", "256");
        defaults.put("top_p", "1");
        defaults.put("frequency_penalty", "0");
        defaults.put("presence_penalty", "0");

        for (Map.Entry<String, String> entry : defaults.entrySet()) {

            SettingDetail settingDetail = SettingDetail.builder()
                                                       .memberId("9dbe75e3-b49b-4f3e-83df-8aaa85e6b95a")
                                                       .settingNo(setting.getSettingNo())
                                                       .settingKey(entry.getKey())
                                                       .settingValue(entry.getValue())
                                                       .build();
            int sdCnt = settingMapper.insertSettingDetails(settingDetail);
        }


    }


}
