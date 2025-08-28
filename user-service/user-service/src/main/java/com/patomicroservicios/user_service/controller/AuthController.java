package com.patomicroservicios.user_service.controller;

import com.patomicroservicios.user_service.dto.request.RegisterUserRequest;
import com.patomicroservicios.user_service.dto.response.RegisterUserResponse;
import com.patomicroservicios.user_service.service.KeycloakAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final KeycloakAdminService keycloakAdminService;

    public AuthController(KeycloakAdminService keycloakAdminService) {
        this.keycloakAdminService = keycloakAdminService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        String userId = keycloakAdminService.registerUser(request);
        return ResponseEntity.status(201).body(new RegisterUserResponse(userId, "Usuario creado"));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable String username) {
        Map<String, Object> user = keycloakAdminService.getUserByUsername(username);

        // Solo devolvemos id y email (si querés limitar)
        Map<String, Object> response = Map.of(
                "id", user.get("id"),
                "email", user.get("email")
        );

        return ResponseEntity.ok(response);
    }

}
