package com.rentalreview.services;

import com.rentalreview.dto.UserDto;
import com.rentalreview.entities.User;
import com.rentalreview.mapper.UserMapper;
import com.rentalreview.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("user with email %s not found", email)));
    }

    @Transactional
    public UserDto signUpUser(User user) {

        var userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("User with %s email already exists".formatted(user.getEmail()));
        }

        var passwordHash = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPasswordHash(passwordHash);

        userRepository.save(user);

        return userMapper.userToUserDto(user);
    }

    public Optional<UserDto> findById(long userId) {
        return userRepository.findById(userId)
                .map(userMapper::userToUserDto);
    }

    public void deleteUserById(long userId) {
        var userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.deleteById(userId);
    }
}
