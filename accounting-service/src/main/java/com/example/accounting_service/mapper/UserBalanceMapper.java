package com.example.accounting_service.mapper;

import com.example.accounting_service.dto.UserBalanceDTO;
import com.example.accounting_service.model.UserBalance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserBalanceMapper {
    UserBalanceDTO toDto(UserBalance userBalance);
}
