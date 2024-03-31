package kr.itycoon.plutoid;

import kr.itycoon.plutoid.biz.llm.domain.LLMModel;
import kr.itycoon.plutoid.biz.llm.mapper.LLMMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j

public class LLMMapperTest {

    @Autowired
    LLMMapper llmMapper;


    @Test
    public void findDefaultLlmModelTest() {

        LLMModel model = llmMapper.findDefaultLlmModel("OPEN_AI");
        log.info("model : {}", model);

        assertTrue(model.getLlmProvider()
                        .equals("OPEN_AI"));
    }


    @Test
    public void findLlmModelListTest() {

        List<LLMModel> modelList = llmMapper.findLlmModelList();
        log.info("model : {}", modelList);

        assertTrue(modelList.size() == 4);
    }


}
