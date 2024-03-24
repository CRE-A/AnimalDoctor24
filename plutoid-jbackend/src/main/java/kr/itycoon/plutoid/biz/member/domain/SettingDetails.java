package kr.itycoon.plutoid.biz.member.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SettingDetails {

    @NonNull
    private String frequency_penalty;

    @NonNull
    private String max_tokens;

    @NonNull
    private String presence_penalty;

    @NonNull
    private String temperature;

    @NonNull
    private String top_p;

    @Builder
    public SettingDetails(String frequency_penalty, String max_tokens, String presence_penalty, String temperature, String top_p) {
        this.frequency_penalty = frequency_penalty;
        this.max_tokens = max_tokens;
        this.presence_penalty = presence_penalty;
        this.temperature = temperature;
        this.top_p = top_p;
    }
}
