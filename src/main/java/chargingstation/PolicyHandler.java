package chargingstation;

import chargingstation.config.kafka.KafkaProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// import com.fasterxml.jackson.databind.DeserializationFeature;
// import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired 
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_CancelPay(@Payload OrderCanceled orderCanceled){

        if(!orderCanceled.validate()) return;

        System.out.println("$$$$$ listener CancelPay : " + orderCanceled.toJson() + "$$$$$");

        if (orderCanceled.getOrderStatus().equals("ORDER_CANCELED")) {
            Payment payment = paymentRepository.findByOrderId(orderCanceled.getId());
            if (payment != null)
            {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = format.format(Calendar.getInstance().getTime());
                payment.setPayTime(dateStr);
                payment.setOrderStatus("PAY_CANCELED");
                
                paymentRepository.delete(payment);

                System.out.println("$$$$$ wheneverOrderCanceled_CancelPay  $$$$$");
            }
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}