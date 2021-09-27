package chargingstation;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
// import java.util.List;
// import java.util.Date;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String orderPackType;
    private Integer orderPackQty;
    private String orderPrice;
    private String orderStatus;
    private String carName;
    private String carNumber;
    private String payTime;
    private String phoneNumber;

    @PostPersist
    public void onPostPersist(){
        if (this.orderStatus.equals("ORDER_PLACED")){
            PayFinished payFinished = new PayFinished();
            BeanUtils.copyProperties(this, payFinished);
            payFinished.setOrderStatus("PAY_FINISHED");
            payFinished.publishAfterCommit();

            payFinished.saveJsonToPvc(payFinished.getOrderStatus(), payFinished.toJson());
            System.out.println("$$$$$ Payment onPostPersist, PAY_FINISHED  $$$$$");
            System.out.println("$$$$$ payFinished : " + payFinished.toJson() + "$$$$$");
        }
    }
    @PostRemove
    public void onPostRemove(){
        if (this.orderStatus.equals("PAY_CANCELED")){
            PayCanceled payCanceled = new PayCanceled();
            BeanUtils.copyProperties(this, payCanceled);
            payCanceled.publishAfterCommit();

            payCanceled.saveJsonToPvc(payCanceled.getOrderStatus(), payCanceled.toJson());

            System.out.println("$$$$$ Payment onPostRemove, PAY_CANCELED  $$$$$");
            System.out.println("$$$$$ payCanceled : " + payCanceled.toJson() + "$$$$$");
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getOrderPackType() {
        return orderPackType;
    }

    public void setOrderPackType(String orderPackType) {
        this.orderPackType = orderPackType;
    }
    public Integer getOrderPackQty() {
        return orderPackQty;
    }

    public void setOrderPackQty(Integer orderPackQty) {
        this.orderPackQty = orderPackQty;
    }
    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }




}