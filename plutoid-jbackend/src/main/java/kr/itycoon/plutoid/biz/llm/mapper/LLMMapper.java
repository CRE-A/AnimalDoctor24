package kr.itycoon.plutoid.biz.llm.mapper;

import kr.itycoon.plutoid.biz.llm.domain.LLMModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LLMMapper {

    // LLM모델 목록 조회
    List<LLMModel> findLlmModelList();

    // 기본 LLM모델 조회
    LLMModel findDefaultLlmModel(String llmProvider);

    // LLM모델 조회
    LLMModel findLlmModel(String llmProvider);


}
