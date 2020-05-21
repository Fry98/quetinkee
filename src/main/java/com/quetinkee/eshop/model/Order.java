package com.quetinkee.eshop.model;

import com.quetinkee.eshop.model.enums.OrderStatus;
import com.quetinkee.eshop.model.enums.PaymentOption;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false, length = 4)
    private OrderStatus status;

    @NotNull(message = "Zadejte typ platby")
    @Basic(optional = false)
    @Column(nullable = false, length = 4)
    private PaymentOption payment;

    @NotNull(message = "Zadejte celkovou cenu objednávky")
    @Digits(integer = 11, fraction = 2, message = "Cena je ve špatném formátu")
    @Min(value = 1, message = "Zadejte celkovou cenu objednávky")
    @Basic(optional = false)
    @Column(nullable = false, columnDefinition = "DECIMAL(11,2)", precision = 11, scale = 2)
    private BigDecimal totalPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = true)
    private User user;

    @NotBlank(message = "Jméno je povinná položka")
    @Column(nullable = false)
    private String userFirstName;

    @NotBlank(message = "Příjmení je povinná položka")
    @Column(nullable = false)
    private String userLastName;

    @NotBlank(message = "Telefonní číslo je povinná položka")
    @Pattern(regexp = "^[0-9]{9}$", message = "Neplatné telefonní číslo")
    @Column(nullable = false)
    private String userPhone;

    @NotBlank(message = "E-mail je povinná položka")
    @Email(message = "Nesprávně zadaný email")
    @Column(nullable = false)
    private String userMail;

    @NotNull(message = "Datum dodání je povinná položka")
    private Date day;

    @NotNull(message = "Čas dodání je povinná položka")
    private Time time;

    private Timestamp created;

    @Valid
    @NotNull(message = "Zadejte doručovací adresu")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = true)
    private OrderAddress userAddressDelivery;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderAddress userAddressBilling;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> contains;

    public Order() {
        java.util.Date date = new java.util.Date();
        this.created = new Timestamp(date.getTime());
    }

    @JsonProperty
    public Set<OrderItem> getContains() {
        return contains;
    }

    @JsonIgnore
    public void setContains(Set<OrderItem> contains) {
        this.contains = contains;
        this.calculateTotalPrice();
    }

    @JsonProperty
    public OrderStatus getStatus() {
        return this.status;
    }

    @JsonIgnore
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentOption getPayment() {
        return payment;
    }

    public void setPayment(PaymentOption payment) {
        this.payment = payment;
    }

    public BigDecimal getTotalPrice(){
        return this.totalPrice;
    }

    public void addOrderItem(Bouquet bouquet, Integer quantity){
        Objects.requireNonNull(bouquet);
        Objects.requireNonNull(quantity);
        if (this.contains == null) {
            this.contains = new HashSet<>();
        }

        Optional<OrderItem> exists = this.contains.stream().filter(
                it -> it.getBouquet().getId().equals(bouquet.getId())).findAny();
        if (exists.isPresent()) {
            this.adjustQuantity(bouquet, quantity);
        }
        else {
            OrderItem pos = new OrderItem(bouquet, quantity);
            pos.setOrder(this);
            this.contains.add(pos);
            this.calculateTotalPrice();
        }
    }

    public void removeOrderItem(Bouquet bouquet){
        Objects.requireNonNull(bouquet);
        contains.removeIf(pos -> bouquet == pos.getBouquet());
        this.calculateTotalPrice();
    }

    public void removeOrderItem(OrderItem pos){
        Objects.requireNonNull(pos);
        contains.remove(pos);
        this.calculateTotalPrice();
    }

    public void adjustQuantity(Bouquet bouquet, Integer quantity){
        Objects.requireNonNull(bouquet);
        for(OrderItem pos : contains){
            if (bouquet == pos.getBouquet()) {
                pos.setQuantity(quantity);
            }
        }
        this.calculateTotalPrice();
    }

    public void calculateTotalPrice(){
        this.totalPrice = new BigDecimal(0);
        for(OrderItem pos : contains){
            this.totalPrice = this.totalPrice.add(pos.getBouquet().getPriceDec().multiply(new BigDecimal(pos.getQuantity())));
        }
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserAddressBilling(OrderAddress userAddressBilling) {
        this.userAddressBilling = userAddressBilling;
    }

    public OrderAddress getUserAddressBilling() {
        return userAddressBilling;
    }

    public void setUserAddressDelivery(OrderAddress userAddressDelivery) {
        this.userAddressDelivery = userAddressDelivery;
    }

    public OrderAddress getUserAddressDelivery() {
        return userAddressDelivery;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @JsonProperty
    public Timestamp getCreated() {
        return this.created;
    }

    @JsonIgnore
    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
