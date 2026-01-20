package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.response.PaymentInvoiceDtoResponse;
import com.example.sweezcustoms.dto.view.PaymentDetailDtoView;
import com.example.sweezcustoms.dto.view.PaymentInvoiceDtoView;
import com.example.sweezcustoms.entity.InvoiceDescriptionEntity;
import com.example.sweezcustoms.entity.PaymentInvoiceEntity;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PaymentInvoiceMapper {
    @Autowired
    @Lazy
    protected DeclarationMapper declarationMapper;
    @Autowired
    @Lazy
    protected DeclarationProductMapper declarationProductMapper;

    @Mapping(target = "participantDto", expression = "java(ParticipantMapper.toParticipantDto(paymentInvoiceEntity.getParticipant()))")
    @Mapping(target = "declarationDtoView", expression = "java(declarationMapper.toDtoView(paymentInvoiceEntity.getDeclarationEntity()))")
    abstract PaymentInvoiceDtoResponse toDtoResponse(PaymentInvoiceEntity paymentInvoiceEntity);

    abstract List<PaymentInvoiceDtoResponse> toDtoResponseList(List<PaymentInvoiceEntity> paymentInvoiceEntities);

    abstract PaymentInvoiceDtoView toDtoView(PaymentInvoiceEntity paymentInvoiceEntity);

    abstract List<PaymentInvoiceDtoView> toDtoViewList(List<PaymentInvoiceEntity> paymentInvoiceEntities);

    @Mapping(target = "sourceProduct", expression = "java(declarationProductMapper.toDtoView(invoiceDescriptionEntity.getSourceProduct()))")
    abstract PaymentDetailDtoView toDtoDetailView(InvoiceDescriptionEntity invoiceDescriptionEntity);

}
