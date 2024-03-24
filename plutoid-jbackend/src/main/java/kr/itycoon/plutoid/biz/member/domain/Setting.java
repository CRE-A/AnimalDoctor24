package kr.itycoon.plutoid.biz.member.domain;

import kr.itycoon.plutoid.biz.common.model.BaseDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Setting extends BaseDto {

    @NonNull
    private String memberId;

    @NonNull
    private Integer settingNo;

    @NonNull
    private String llmProvider;

    @NonNull
    private String llmModel;

    @NonNull
    private Date settingDate;

    @NonNull
    private String defaultValueYn;

    private SettingDetails settingDetails;

    @Builder
    public Setting(String memberId, Integer settingNo, String llmProvider, String llmModel, Date settingDate, String defaultValueYn, SettingDetails settingDetails) {
        this.memberId = memberId;
        this.settingNo = settingNo;
        this.llmProvider = llmProvider;
        this.llmModel = llmModel;
        this.settingDate = settingDate;
        this.defaultValueYn = defaultValueYn;
        this.settingDetails = settingDetails;
    }

}
