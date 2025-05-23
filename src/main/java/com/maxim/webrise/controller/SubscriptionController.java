package com.maxim.webrise.controller;

import com.maxim.webrise.controller.dto.ReadSubscribeDto;
import com.maxim.webrise.controller.mapper.SubscriptionMapper;
import com.maxim.webrise.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
@Tag(name = "Subscription Controller", description = "Управление подписками пользователей")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionController(SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper) {
        this.subscriptionService = subscriptionService;
        this.subscriptionMapper = subscriptionMapper;
    }

    @PostMapping
    @Operation(summary = "Добавить подписку пользователю")
    public ResponseEntity<Void> add(@PathVariable Long userId, @RequestParam Long subscriptionId) {
        subscriptionService.addSubscription(userId, subscriptionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Получить подписки пользователя")
    public ResponseEntity<List<ReadSubscribeDto>> list(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId)
                .stream()
                .map(subscriptionMapper::toDto)
                .toList());
    }

    @DeleteMapping("/{subscriptionId}")
    @Operation(summary = "Удалить подписку у пользователя")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }
}
