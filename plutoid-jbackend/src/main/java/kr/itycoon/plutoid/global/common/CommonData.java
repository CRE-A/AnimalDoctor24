package kr.itycoon.plutoid.global.common;

import org.springframework.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class CommonData {

    @NonNull
    private final String memberId;

    @Nullable
    private final String email;

    @Nullable
    private final MemberRoleEnum memberRole;

    @NonNull
    private final String ipAddress;

    @NonNull
    private final String requestUri;

    @NonNull
    private final String requestMethod;

    @Builder
    public CommonData(String memberId, String email, MemberRoleEnum memberRole, String ipAddress, String requestUri,
            String requestMethod) {
        super();
        this.memberId = memberId;
        this.email = email;
        this.memberRole = memberRole;
        this.ipAddress = ipAddress;
        this.requestUri = requestUri;
        this.requestMethod = requestMethod;
    }
    
    

}
