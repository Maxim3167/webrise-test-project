package com.maxim.webrise.controller;

import com.maxim.webrise.controller.dto.ReadSubscribeDto;
import com.maxim.webrise.controller.mapper.SubscriptionMapper;
import com.maxim.webrise.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@Tag(name = "Common Subscription Controller", description = "Базовый контроллер для работы с подписками")
public class CommonSubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    public CommonSubscriptionController(SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper) {
        this.subscriptionService = subscriptionService;
        this.subscriptionMapper = subscriptionMapper;
    }

    @GetMapping("/top")
    @Operation(summary = "Получить топ-3 популярных подписок")
    public ResponseEntity<List<ReadSubscribeDto>> getTopSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTopSubscriptions(3).stream()
                .map(subscriptionMapper::toDto)
                .toList());
    }
}

