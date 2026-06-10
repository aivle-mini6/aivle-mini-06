package com.aivle.bookapp.service;

import com.aivle.bookapp.domain.User;
import com.aivle.bookapp.dto.request.UserLoginRequest;
import com.aivle.bookapp.dto.request.UserRegisterRequest;
import com.aivle.bookapp.exception.UserNotFoundException;
import com.aivle.bookapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void register(UserRegisterRequest request) {
        // 1. 필수 필드 누락 검사 (하나라도 비어있으면 예외 처리)
        if (request.getUserId() == null || request.getUserId().isBlank()) {
            throw new IllegalArgumentException("아이디는 필수 입력 항목입니다.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력 항목입니다.");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("이름은 필수 입력 항목입니다.");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("이메일은 필수 입력 항목입니다.");
        }
        if (request.getNickname() == null || request.getNickname().isBlank()) {
            throw new IllegalArgumentException("닉네임은 필수 입력 항목입니다.");
        }

        Optional<User> findUser = userRepository.findByUserId(request.getUserId());

        if (findUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(UserLoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 아이디입니다."));

        if(!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return "환영합니다. " + user.getName() + "님!";
    }

}
