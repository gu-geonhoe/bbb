package com.example.apitest.oauth;

import com.example.apitest.User.entity.User;
import com.example.apitest.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUserName(userName); //repository 로부터 정보를 꺼낸다.
        return new PrincipalDetails(userEntity); // 꺼낸 정보를 principal 형태로 형변환 후 반환
    }
}