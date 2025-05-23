package com.maxim.webrise.repository;

import com.maxim.webrise.repository.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    @Query("select us from UserSubscription us join fetch us.user join fetch us.subscription")
    List<UserSubscription> findByUserId(Long userId);
    int deleteByUserIdAndSubscriptionId(Long userId, Long subscriptionId);
}
