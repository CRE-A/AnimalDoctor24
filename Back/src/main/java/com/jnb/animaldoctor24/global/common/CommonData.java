package com.jnb.animaldoctor24.global.common;

import com.jnb.animaldoctor24.domain.member.domain.Role;
import io.micrometer.common.lang.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@ToString
public class CommonData {
    @Nullable
    private final String email;

    @Nullable
    private final Role role;

    @NonNull
    private final String ipAddress;

    @NonNull
    private final String requestUri;

    @NonNull
    private final String requestMethod;

    @Builder
    public CommonData(String email, Role role, String ipAddress, String requestUri,
                      String requestMethod) {
        super();
        this.email = email;
        this.role = role;
        this.ipAddress = ipAddress;
        this.requestUri = requestUri;
        this.requestMethod = requestMethod;
    }
}
