package com.re.bt4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "seller_registrations")
public class SellerRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String fullName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 20)
    private String identityNumber;

    @Column(nullable = false)
    private String contactAddress;

    @Column(nullable = false, length = 120)
    private String shopName;

    @Column(nullable = false, length = 80)
    private String shopCategory;

    @Column(nullable = false)
    private String shopAddress;

    @Column(nullable = false, length = 13)
    private String taxCode;

    @Column(length = 500)
    private String shopDescription;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected SellerRegistration() {
    }

    public SellerRegistration(
            String fullName,
            String email,
            String phone,
            String identityNumber,
            String contactAddress,
            String shopName,
            String shopCategory,
            String shopAddress,
            String taxCode,
            String shopDescription) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.identityNumber = identityNumber;
        this.contactAddress = contactAddress;
        this.shopName = shopName;
        this.shopCategory = shopCategory;
        this.shopAddress = shopAddress;
        this.taxCode = taxCode;
        this.shopDescription = shopDescription;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
