package com.rentalreview.services;

import com.rentalreview.entities.User;
import com.rentalreview.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("user with email %s not found", email)));
    }

    public User signUpUser(User user) {

        var userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("User with %s email already exists".formatted(user.getEmail()));
        }

        var passwordHash = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPasswordHash(passwordHash);

        return userRepository.save(user);
    }

}
