package kr.itycoon.plutoid.biz.llm.domain;

import kr.itycoon.plutoid.biz.common.model.BaseDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class LLMModel extends BaseDto {

    private String llmProvider;
    private String llmModel;
    private String maxToken;
    private String defaultModelYn;

    @Builder
    public LLMModel(String llmProvider, String llmModel, String maxToken, String defaultModelYn) {
        this.llmProvider = llmProvider;
        this.llmModel = llmModel;
        this.maxToken = maxToken;
        this.defaultModelYn = defaultModelYn;
    }
}
