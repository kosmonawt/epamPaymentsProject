package dao.impl;

import dao.DaoConverter;
import dto.PaymentDTO;
import entity.Payment;
import entity.PaymentStatus;

import java.math.BigInteger;

/**
 * Convert Payment entity to DTO and back
 */

public class PaymentConverter implements DaoConverter<PaymentDTO, Payment> {

    //TODO add field with payment number to converter and in DTO <-> DAO

    @Override
    public Payment convertTo(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setPaymentFromAccount(BigInteger.valueOf(paymentDTO.getPaymentFromAccount()));
        payment.setPaymentToAccount(BigInteger.valueOf(paymentDTO.getPaymentToAccount()));
        payment.setAmount(paymentDTO.getAmount());
        payment.setDateTime(paymentDTO.getDateTime());
        payment.setPaymentStatus(PaymentStatus.valueOf(paymentDTO.getPaymentStatus().trim().toUpperCase()));
        return payment;
    }

    @Override
    public PaymentDTO convertFrom(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(paymentDTO.getId());
        paymentDTO.setPaymentFromAccount(payment.getPaymentFromAccount().longValue());
        paymentDTO.setPaymentToAccount(payment.getPaymentToAccount().longValue());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setDateTime(payment.getDateTime());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus().name());
        return paymentDTO;
    }
}
