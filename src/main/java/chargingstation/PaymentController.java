package chargingstation;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @PostMapping("/pay")
    public boolean pay(@RequestBody Payment payment){
        System.out.println("$$$$$ /payment/pay  called $$$$$");

        payment.setOrderStatus("ORDER_PLACED");
        paymentRepository.save(payment);
        
        /*
        // CB test 용 지연 코드.
        try {
        Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        */
        return true;
    }
}
