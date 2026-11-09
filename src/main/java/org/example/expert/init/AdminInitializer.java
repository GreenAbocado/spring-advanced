package org.example.expert.init;

import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.service.AuthService;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("local")
public class AdminInitializer implements CommandLineRunner {

	// 기존 회원가입 로직 재사용을 통한 해시화 등 정책 적용
	private final AuthService authService;

	// 저장된 사용자를 조회하여 관리자 권한으로 변경
	private final UserRepository userRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		String email = "www.@www.com";
		SignupRequest signupRequest = new SignupRequest(email, "A123456789");
		authService.signup(signupRequest);
		userRepository.findByEmail(email).orElseThrow().updateRole(UserRole.ADMIN);
	}
}