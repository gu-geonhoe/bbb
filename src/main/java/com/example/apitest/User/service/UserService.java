package com.example.apitest.User.service;

import com.example.apitest.User.entity.User;
import com.example.apitest.User.repository.UserRepository;
import com.example.apitest.exception.BusinessLogicException;
import com.example.apitest.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        User savedUser = user;
        return userRepository.save(savedUser);
    }


    public User updateUser(User user) {
        User findUser = findVerifiedUser(user.getUserId());

        Optional.ofNullable(user.getUserName())
                .ifPresent(name -> findUser.setUserName(name));
        Optional.ofNullable(user.getEmail())
                .ifPresent(email -> findUser.setEmail(email));
        Optional.ofNullable(user.getPassword())
                .ifPresent(password -> findUser.setPassword(password));


        return userRepository.save(findUser);
    }


    public User findUser(long userId) {
        return  findVerifiedUser(userId);
    }

    public Page<User> findUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size,
                Sort.by("userId").descending()));
    }

    public void deleteUser(long userId) {
        User findUser = findVerifiedUser(userId);
        userRepository.delete(findUser);

    }

    public User findVerifiedUser(long userId) {
        Optional<User> optionalMember =
                userRepository.findById(userId);
        User findUser =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }
    private void verifyExistsEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent())
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
    }

  /*  public VisitorStatus verifyUserNameAndPassword(String userName, String password) {
        Optional<User> user1 = userRepository.findByUserName(userName);
        Optional<User> user2 = userRepository.findByPassword(password);
        User loginUser = new User();
        if ((user1.isPresent()) && (user2.isPresent())) {
            // 해당 이름과 비밀번호를 가진 user 객체가 있을 때
            // 이름으로 찾은 user1과 비밀번호로 찾은 user2의 userId가 동일하면 로그인 성공
            //  아니면 로그인 실패
            long user1Id = user1.get().getUserId();
            long user2Id = user2.get().getUserId();
            if(user1Id == user2Id){
                VisitorStatus.StaticVisitorStatus.setAuth(1);
                VisitorStatus.StaticVisitorStatus.setId(user1.get().getUserId());
            }
        }

        return VisitorStatus.StaticVisitorStatus;


    }*/

}