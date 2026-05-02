package com.example.accounting_service.mapper;

import com.example.accounting_service.dto.PaymentRecordDTO;
import com.example.accounting_service.model.PaymentRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRecordMapper {
    PaymentRecordDTO toDto(PaymentRecord paymentRecord);
}
