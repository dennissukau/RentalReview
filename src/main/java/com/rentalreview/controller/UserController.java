package com.rentalreview.controller;

import com.rentalreview.dto.ReviewDto;
import com.rentalreview.dto.UserDto;
import com.rentalreview.mapper.UserMapper;
import com.rentalreview.repositories.UserRepository;
import com.rentalreview.services.ReviewService;
import com.rentalreview.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final ReviewService reviewService;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @DeleteMapping
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/review")
    public ResponseEntity<List<ReviewDto>> getReviewsByUser(@PathVariable Long userId) {
        var reviewsOfUser = reviewService.getReviewsByUser(userId);
        return new ResponseEntity<>(reviewsOfUser, HttpStatus.OK);
    }
}
