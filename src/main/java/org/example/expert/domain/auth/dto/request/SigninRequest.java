package org.example.expert.domain.auth.dto.request;

import static org.example.expert.domain.auth.common.ValidationConst.*;

import jakarta.validation.constraints.NotBlank;

/* 레코드를 통한 간소화 및 불변 안정성 + 메시지 추가
   서비스에서 단순 비교에 사용되는 값이므로 NotBlank 검증만 수행 */
public record SigninRequest(
	@NotBlank(message = EMAIL_IS_BLANK)
	String email,

	@NotBlank(message = PASSWORD_IS_BLANK)
	String password
) {
	// 이메일 공백 제거를 통한 데이터 일관성 확보
	public SigninRequest {
		email = (email == null) ? null : email.trim();
	}
}