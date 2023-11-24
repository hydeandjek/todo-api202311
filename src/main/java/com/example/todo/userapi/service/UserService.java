package com.example.todo.userapi.service;


import com.example.todo.userapi.dto.UserRequestSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.User;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입 처리
    public UserSignUpResponseDTO create(final UserRequestSignUpDTO dto){

        String email = dto.getEmail();

        if (userRepository.existsByEmail(email)){
            log.warn("이메일이 중복 되었습니다 . -{}",email);
            throw  new RuntimeException("중복된 이메일 입니다.");
        }

        // 페스워드 인코딩
        String encoded = passwordEncoder.encode(dto.getPassword());

        dto.setPassword(encoded);

        // dto를 User Entity로 변환해서
        User saved = userRepository.save(dto.toEntity());
        log.info("회원 가입 정상 수행됨! - saved user - {} ", saved);

        return new UserSignUpResponseDTO(saved);
    }

}