package org.example.expert.domain.auth.dto.request;

import static org.example.expert.domain.auth.common.ValidationConst.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/* 레코드를 통한 간소화 및 불변 안정성 + 메시지 추가
*  DTO에서 UserRole 제거를 통해 관리자만 권한 변경 가능하도록 설정 -> 안전성 */
public record SignupRequest(
	@NotBlank(message = EMAIL_IS_BLANK)
	@Email(message = "유효한 이메일 형식을 입력하세요.")
	String email,

	@NotBlank(message = PASSWORD_IS_BLANK)
	@Pattern(
		regexp = "(?=.*\\d)(?=.*[A-Z]).{8,}",
		message = "비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.")
	String password
) {
	// 이메일 공백 제거를 통한 데이터 일관성 확보
	public SignupRequest {
		email = (email == null) ? null : email.trim();
	}
}