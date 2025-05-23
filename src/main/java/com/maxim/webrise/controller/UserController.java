package com.maxim.webrise.controller;

import com.maxim.webrise.controller.dto.ReadUserDto;
import com.maxim.webrise.controller.dto.UserDto;
import com.maxim.webrise.controller.mapper.UserMapper;
import com.maxim.webrise.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Управление пользователями")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public ResponseEntity<ReadUserDto> create(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userMapper.toDto(userService.create(userMapper.toEntity(userDto))));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public ResponseEntity<ReadUserDto> get(@PathVariable Long id) {
        return userService.get(id)
                .map(body -> ResponseEntity.ok(userMapper.toDto(body)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные пользователя")
    public ResponseEntity<ReadUserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userMapper.toDto(userService.update(id, userMapper.toEntity(userDto))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
