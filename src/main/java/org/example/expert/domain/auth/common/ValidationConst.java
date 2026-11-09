package org.example.expert.domain.auth.common;

/* 중복된 검증 패턴과 메시지를 상수화로 관리 복잡도 완화 */
public class ValidationConst {

	public static final String EMAIL_IS_BLANK = "이메일이 입력되지 않았습니다.";
	public static final String PASSWORD_IS_BLANK = "비밀번호가 입력되지 않았습니다.";


	private ValidationConst() {}
}
