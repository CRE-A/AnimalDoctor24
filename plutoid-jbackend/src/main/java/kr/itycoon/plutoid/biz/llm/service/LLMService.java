package kr.itycoon.plutoid.biz.llm.service;

import kr.itycoon.plutoid.biz.llm.domain.LLMModel;
import kr.itycoon.plutoid.biz.llm.mapper.LLMMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMService {

    @Autowired
    LLMMapper llmMapper;

    //private final LLMMapper llmMapper;

    // LLM모델 목록 조회
    public List<LLMModel> getLlmModelList() {
        return llmMapper.findLlmModelList();
    }

    // 기본 LLM모델 조회
    public LLMModel findDefaultLlmModel(String llmProvider) {
        return llmMapper.findDefaultLlmModel(llmProvider);
    }

    // LLM모델 조회
    public LLMModel findLlmModel(String llmModel) {
        return llmMapper.findLlmModel(llmModel);
    }


}
