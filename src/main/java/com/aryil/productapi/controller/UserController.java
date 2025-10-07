package com.aryil.productapi.controller;

import com.aryil.productapi.dto.UserDTO;
import com.aryil.productapi.dto.request.UserRegisterRequest;
import com.aryil.productapi.messaging.MessageProducer;
import com.aryil.productapi.service.UserService;
import com.aryil.productapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final MessageProducer messageProducer;

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegisterRequest request) {
        UserDTO dto = userMapper.toDto(
                userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail())
        );
        messageProducer.sendCategoryMessage("Yeni user oluşturuldu: " + dto.getUsername());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
