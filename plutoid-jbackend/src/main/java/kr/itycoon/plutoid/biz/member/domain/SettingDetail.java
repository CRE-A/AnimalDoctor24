package kr.itycoon.plutoid.biz.member.domain;


import kr.itycoon.plutoid.biz.common.model.BaseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SettingDetail extends BaseDto {

    @NonNull
    private String memberId;

    @NonNull
    private Integer settingNo;

    @NonNull
    private String settingKey;

    @NonNull
    private String settingValue;

    @Builder
    public SettingDetail(@NonNull String memberId, @NonNull Integer settingNo, @NonNull String settingKey, @NonNull String settingValue) {
        this.memberId = memberId;
        this.settingNo = settingNo;
        this.settingKey = settingKey;
        this.settingValue = settingValue;
    }
}
