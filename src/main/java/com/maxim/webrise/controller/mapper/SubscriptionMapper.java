package com.maxim.webrise.controller.mapper;

import com.maxim.webrise.controller.dto.ReadSubscribeDto;
import com.maxim.webrise.repository.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    ReadSubscribeDto toDto(Subscription subscription);
}
