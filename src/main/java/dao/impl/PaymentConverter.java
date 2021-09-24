package dao.impl;

import dao.DaoConverter;
import dto.PaymentDTO;
import entity.Payment;
import entity.PaymentStatus;

import java.math.RoundingMode;

/**
 * Convert Payment entity to DTO and back
 */

public class PaymentConverter implements DaoConverter<PaymentDTO, Payment> {

    @Override
    public Payment convertTo(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setPaymentNum(paymentDTO.getPaymentNum());
        payment.setPaymentFromAccount(paymentDTO.getPaymentFromAccount());
        payment.setPaymentToAccount(paymentDTO.getPaymentToAccount());
        payment.setAmount(paymentDTO.getAmount().setScale(2, RoundingMode.HALF_UP));
        payment.setDateTime(paymentDTO.getDateTime());
        payment.setPaymentStatus(PaymentStatus.valueOf(paymentDTO.getPaymentStatus().trim().toUpperCase()));
        payment.setSender(paymentDTO.getSender());
        payment.setRecipient(paymentDTO.getRecipient());
        return payment;
    }

    @Override
    public PaymentDTO convertFrom(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setPaymentNum(payment.getPaymentNum());
        paymentDTO.setPaymentFromAccount(payment.getPaymentFromAccount());
        paymentDTO.setPaymentToAccount(payment.getPaymentToAccount());
        paymentDTO.setAmount(payment.getAmount().setScale(2, RoundingMode.HALF_UP));
        paymentDTO.setDateTime(payment.getDateTime());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus().name());
        paymentDTO.setSender(payment.getSender());
        paymentDTO.setRecipient(payment.getRecipient());
        return paymentDTO;
    }
}
