package org.example.expert.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.common.JwtUtil;
import org.example.expert.common.PasswordEncoder;
import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.dto.response.AuthTokenResponse;
import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthTokenResponse signup(SignupRequest signupRequest) {

		if (userRepository.existsByEmail(signupRequest.email())) {
			throw new InvalidRequestException("이미 존재하는 이메일입니다.");
		}

        User newUser = new User(
                signupRequest.email(),
				passwordEncoder.encode(signupRequest.password())
        );
        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(savedUser.getId(), savedUser.getEmail(), savedUser.getUserRole());
        return new AuthTokenResponse(bearerToken);
    }

    @Transactional(readOnly = true)
    public AuthTokenResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.email()).orElse(null);

        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401을 반환합니다.
		// 특정 이메일 식별 방지를 위해 이메일과 비번을 같은 예외 메시지로 처리
        if (user == null || !passwordEncoder.matches(signinRequest.password(), user.getPassword())) {
            throw new AuthException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getUserRole());

        return new AuthTokenResponse(bearerToken);
    }
}
