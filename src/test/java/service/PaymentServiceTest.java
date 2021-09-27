package service;

import dto.PaymentDTO;
import entity.PaymentStatus;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class PaymentServiceTest {

    final PaymentService paymentService = PaymentService.getInstance();


    @Test
    public void createPaymentTest() {
        Long accFrom = 17L;
        Long accTo = 26L;
        BigDecimal amount = BigDecimal.valueOf(253);
        String sender = "c@c.c";
        String recipient = "q@q.q";

        int sizeBefore = paymentService.getAllPaymentsByUserEmail(sender).size();
        paymentService.create(accFrom, accTo, amount, sender, recipient);
        int sizeAfter = paymentService.getAllPaymentsByUserEmail(sender).size();
        assertEquals(sizeAfter - sizeBefore, 1);

    }

    @Test
    public void sendPaymentTest() {
        String email = "c@c.c";
        createPaymentTest();
        List<PaymentDTO> payments = paymentService.getAllPaymentsByUserEmail(email);
        payments = payments.stream().filter(x -> x.getPaymentStatus().equals(PaymentStatus.PREPARED.name()))
                .sorted(Comparator.comparing(PaymentDTO::getPaymentNum).reversed())
                .collect(Collectors.toList());

        for (PaymentDTO paymentDTO : payments) {
            System.out.println(paymentDTO.getPaymentNum());
        }

        Long accNum = payments.get(0).getPaymentNum().longValue();
        System.out.println("Payment status for payment num: " + accNum + " is " + payments.get(0).getPaymentStatus());
        System.out.println("Changes status for payment number : " + accNum);
        paymentService.sendPayment(accNum);
    }

    @Test
    public void approvePaymentTest() {

        createPaymentTest();
        sendPaymentTest();
        List<PaymentDTO> paymentsBefore = paymentService.getAllPaymentsWithStatusSend();
        paymentsBefore.sort(Comparator.comparing(PaymentDTO::getPaymentNum).reversed());

        System.out.println("Payment number is " + paymentsBefore.get(0).getPaymentNum().longValue());

        int sizeBefore = paymentsBefore.size();
        paymentService.approvePayment(paymentsBefore.get(0).getPaymentNum().longValue(), "uk");
        List<PaymentDTO> paymentsAfter = paymentService.getAllPaymentsWithStatusSend();
        int sizeAfter = paymentsAfter.size();

        assertEquals(sizeBefore - sizeAfter, 1);
    }

}
