package org.landm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.landm.dto.UserDto;
import org.landm.dto.auth.RegisterUserRequestDto;
import org.landm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Users", description = "Registracija i upravljanje korisnicima")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registracija novog korisnika", description = "Email mora biti jedinstven")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserRequestDto request) {

        UserDto created = userService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
}