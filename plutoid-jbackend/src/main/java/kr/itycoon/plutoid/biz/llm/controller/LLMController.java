package kr.itycoon.plutoid.biz.llm.controller;

import kr.itycoon.plutoid.biz.llm.domain.LLMModel;
import kr.itycoon.plutoid.biz.llm.service.LLMService;
import kr.itycoon.plutoid.global.common.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LLMController {

    private final LLMService llmService;

    @GetMapping("/api/v1/llms")
    public ResponseEntity<CommonResult> getLLMModel() {
        log.debug("llmModel 받아오기전 : {} ");
        List<LLMModel> llmModelList = llmService.getLlmModelList();
        log.debug("llmModel 받아온 후 : {} ", llmModelList);
        return new ResponseEntity<CommonResult>(new CommonResult(llmModelList), HttpStatus.OK);
    }


}
