package kr.itycoon.plutoid.biz.member.dto;

import kr.itycoon.plutoid.biz.member.domain.SettingDetails;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class LLMSettingRequest {

    @NonNull
    private String memberId;

    @NonNull
    private String llmProvider;

    @NonNull
    private String llmModel;

    private SettingDetails settingDetails;

    @Builder
    public LLMSettingRequest(@NonNull String memberId, @NonNull String llmProvider, @NonNull String llmModel, SettingDetails settingDetails) {
        this.memberId = memberId;
        this.llmProvider = llmProvider;
        this.llmModel = llmModel;
        this.settingDetails = settingDetails;
    }

}
