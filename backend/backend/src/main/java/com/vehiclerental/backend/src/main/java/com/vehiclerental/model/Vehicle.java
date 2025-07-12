package com.vehiclerental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Vehicle registration number is required")
    @Column(name = "registration_number", unique = true)
    private String registrationNumber;
    
    @NotBlank(message = "Make is required")
    private String make;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be valid")
    @Max(value = 2030, message = "Year cannot be in the future")
    private Integer year;
    
    @NotBlank(message = "Color is required")
    private String color;
    
    @NotNull(message = "Vehicle type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;
    
    @NotNull(message = "Fuel type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;
    
    @NotNull(message = "Transmission type is required")
    @Enumerated(EnumType.STRING)
    private TransmissionType transmission;
    
    @NotNull(message = "Seating capacity is required")
    @Min(value = 1, message = "Seating capacity must be at least 1")
    @Column(name = "seating_capacity")
    private Integer seatingCapacity;
    
    @NotNull(message = "Daily rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily rate must be greater than 0")
    @Column(name = "daily_rate", precision = 10, scale = 2)
    private BigDecimal dailyRate;
    
    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;
    
    @Column(name = "vin_number", unique = true)
    private String vinNumber;
    
    @Column(name = "license_plate")
    private String licensePlate;
    
    @Column(name = "insurance_number")
    private String insuranceNumber;
    
    @Column(name = "last_service_date")
    private LocalDateTime lastServiceDate;
    
    @Column(name = "next_service_date")
    private LocalDateTime nextServiceDate;
    
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    
    @Column(name = "location")
    private String location;
    
    @Column(length = 1000)
    private String features;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals;
    
    public enum VehicleType {
        CAR, SUV, TRUCK, VAN, MOTORCYCLE, CONVERTIBLE, SEDAN, HATCHBACK
    }
    
    public enum FuelType {
        PETROL, DIESEL, ELECTRIC, HYBRID, CNG
    }
    
    public enum TransmissionType {
        MANUAL, AUTOMATIC, CVT
    }
    
    public enum VehicleStatus {
        AVAILABLE, RENTED, MAINTENANCE, OUT_OF_SERVICE
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = VehicleStatus.AVAILABLE;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Vehicle() {}
    
    public Vehicle(String registrationNumber, String make, String model, Integer year, String color,
                  VehicleType vehicleType, FuelType fuelType, TransmissionType transmission,
                  Integer seatingCapacity, BigDecimal dailyRate, Integer mileage) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.seatingCapacity = seatingCapacity;
        this.dailyRate = dailyRate;
        this.mileage = mileage;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
    
    public FuelType getFuelType() { return fuelType; }
    public void setFuelType(FuelType fuelType) { this.fuelType = fuelType; }
    
    public TransmissionType getTransmission() { return transmission; }
    public void setTransmission(TransmissionType transmission) { this.transmission = transmission; }
    
    public Integer getSeatingCapacity() { return seatingCapacity; }
    public void setSeatingCapacity(Integer seatingCapacity) { this.seatingCapacity = seatingCapacity; }
    
    public BigDecimal getDailyRate() { return dailyRate; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }
    
    public Integer getMileage() { return mileage; }
    public void setMileage(Integer mileage) { this.mileage = mileage; }
    
    public String getVinNumber() { return vinNumber; }
    public void setVinNumber(String vinNumber) { this.vinNumber = vinNumber; }
    
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    
    public String getInsuranceNumber() { return insuranceNumber; }
    public void setInsuranceNumber(String insuranceNumber) { this.insuranceNumber = insuranceNumber; }
    
    public LocalDateTime getLastServiceDate() { return lastServiceDate; }
    public void setLastServiceDate(LocalDateTime lastServiceDate) { this.lastServiceDate = lastServiceDate; }
    
    public LocalDateTime getNextServiceDate() { return nextServiceDate; }
    public void setNextServiceDate(LocalDateTime nextServiceDate) { this.nextServiceDate = nextServiceDate; }
    
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<Rental> getRentals() { return rentals; }
    public void setRentals(List<Rental> rentals) { this.rentals = rentals; }
    
    public String getDisplayName() {
        return year + " " + make + " " + model;
    }
    
    public boolean isAvailable() {
        return status == VehicleStatus.AVAILABLE;
    }
}
