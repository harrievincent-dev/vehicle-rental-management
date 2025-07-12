package com.vehiclerental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Rental number is required")
    @Column(name = "rental_number", unique = true)
    private String rentalNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @NotNull(message = "Rental start date is required")
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @NotNull(message = "Rental end date is required")
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;
    
    @NotNull(message = "Pickup location is required")
    @Column(name = "pickup_location")
    private String pickupLocation;
    
    @NotNull(message = "Return location is required")
    @Column(name = "return_location")
    private String returnLocation;
    
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "security_deposit", precision = 10, scale = 2)
    private BigDecimal securityDeposit;
    
    @Column(name = "additional_charges", precision = 10, scale = 2)
    private BigDecimal additionalCharges;
    
    @Enumerated(EnumType.STRING)
    private RentalStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
    
    @Column(name = "start_mileage")
    private Integer startMileage;
    
    @Column(name = "end_mileage")
    private Integer endMileage;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum RentalStatus {
        RESERVED, ACTIVE, COMPLETED, CANCELLED, OVERDUE
    }
    
    public enum PaymentStatus {
        PENDING, PAID, PARTIAL, REFUNDED
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = RentalStatus.RESERVED;
        }
        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Rental() {}
    
    public Rental(String rentalNumber, Customer customer, Vehicle vehicle, LocalDate startDate,
                 LocalDate endDate, String pickupLocation, String returnLocation, BigDecimal totalAmount) {
        this.rentalNumber = rentalNumber;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocation = pickupLocation;
        this.returnLocation = returnLocation;
        this.totalAmount = totalAmount;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRentalNumber() { return rentalNumber; }
    public void setRentalNumber(String rentalNumber) { this.rentalNumber = rentalNumber; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public LocalDate getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }
    
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    
    public String getReturnLocation() { return returnLocation; }
    public void setReturnLocation(String returnLocation) { this.returnLocation = returnLocation; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public BigDecimal getSecurityDeposit() { return securityDeposit; }
    public void setSecurityDeposit(BigDecimal securityDeposit) { this.securityDeposit = securityDeposit; }
    
    public BigDecimal getAdditionalCharges() { return additionalCharges; }
    public void setAdditionalCharges(BigDecimal additionalCharges) { this.additionalCharges = additionalCharges; }
    
    public RentalStatus getStatus() { return status; }
    public void setStatus(RentalStatus status) { this.status = status; }
    
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public Integer getStartMileage() { return startMileage; }
    public void setStartMileage(Integer startMileage) { this.startMileage = startMileage; }
    
    public Integer getEndMileage() { return endMileage; }
    public void setEndMileage(Integer endMileage) { this.endMileage = endMileage; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public long getRentalDays() {
        LocalDate end = actualReturnDate != null ? actualReturnDate : endDate;
        return ChronoUnit.DAYS.between(startDate, end) + 1;
    }
    
    public boolean isOverdue() {
        return actualReturnDate == null && LocalDate.now().isAfter(endDate);
    }
    
    public Integer getTotalMileage() {
        if (startMileage != null && endMileage != null) {
