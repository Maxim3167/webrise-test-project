package com.maxim.webrise.service;

import com.maxim.webrise.exception.SubscribeAlreadyExistsException;
import com.maxim.webrise.repository.SubscriptionRepository;
import com.maxim.webrise.repository.UserRepository;
import com.maxim.webrise.repository.UserSubscriptionRepository;
import com.maxim.webrise.repository.entity.Subscription;
import com.maxim.webrise.repository.entity.User;
import com.maxim.webrise.repository.entity.UserSubscription;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Transactional
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserRepository userRepository,
                               UserSubscriptionRepository userSubscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    public void addSubscription(Long userId, Long subscriptionId) {
        log.info("Adding subscription {} to user {}", subscriptionId, userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " is not found"));
        checkSubscribe(user, subscriptionId);

        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new EntityNotFoundException("Subscription with id " + subscriptionId + " is not found"));
        userSubscriptionRepository.save(new UserSubscription(null, user, subscription));

        log.info("Subscription {} added to user {}", subscriptionId, userId);
    }

    @Transactional(readOnly = true)
    public List<Subscription> getUserSubscriptions(Long userId) {
        log.info("Fetching subscriptions for user {}", userId);

        List<Subscription> subscriptions = userSubscriptionRepository.findByUserId(userId)
                .stream()
                .map(UserSubscription::getSubscription)
                .toList();

        log.info("Found {} subscriptions for user {}", subscriptions.size(), userId);
        return subscriptions;
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        log.info("Deleting subscription {} from user {}", subscriptionId, userId);
        int removed = userSubscriptionRepository.deleteByUserIdAndSubscriptionId(userId, subscriptionId);
        if (removed == 0) {
            throw new EntityNotFoundException(String.format("user_id %s or subscription_id %s is not found", userId, subscriptionId));
        }
        log.info("Subscription {} deleted from user {}", subscriptionId, userId);
    }

    private void checkSubscribe(User user, Long subscriptionId) {
        for (UserSubscription subscription : user.getSubscriptions()) {
            if (Objects.equals(subscription.getSubscription().getId(), subscriptionId)) {
                throw new SubscribeAlreadyExistsException("Subscribe with id " + subscriptionId + " already exists by user with id " + user.getId());
            }
        }
    }
}
