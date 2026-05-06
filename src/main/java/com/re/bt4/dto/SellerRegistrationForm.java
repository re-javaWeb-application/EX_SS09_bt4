package com.re.bt4.dto;

import com.re.bt4.validation.PersonalStep;
import com.re.bt4.validation.ShopStep;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import org.springframework.util.StringUtils;

public class SellerRegistrationForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Vui lòng nhập họ tên.", groups = PersonalStep.class)
    @Size(max = 80, message = "Họ tên không được vượt quá 80 ký tự.", groups = PersonalStep.class)
    private String fullName;

    @NotBlank(message = "Vui lòng nhập email.", groups = PersonalStep.class)
    @Email(message = "Email không đúng định dạng.", groups = PersonalStep.class)
    @Size(max = 120, message = "Email không được vượt quá 120 ký tự.", groups = PersonalStep.class)
    private String email;

    @NotBlank(message = "Vui lòng nhập số điện thoại.", groups = PersonalStep.class)
    @Pattern(regexp = "^[0-9+\\-\\s]{9,20}$", message = "Số điện thoại không hợp lệ.", groups = PersonalStep.class)
    private String phone;

    @NotBlank(message = "Vui lòng nhập CCCD/CMND.", groups = PersonalStep.class)
    @Size(max = 20, message = "CCCD/CMND không được vượt quá 20 ký tự.", groups = PersonalStep.class)
    private String identityNumber;

    @NotBlank(message = "Vui lòng nhập địa chỉ liên hệ.", groups = PersonalStep.class)
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự.", groups = PersonalStep.class)
    private String contactAddress;

    @NotBlank(message = "Vui lòng nhập tên gian hàng.", groups = ShopStep.class)
    @Size(max = 120, message = "Tên gian hàng không được vượt quá 120 ký tự.", groups = ShopStep.class)
    private String shopName;

    @NotBlank(message = "Vui lòng chọn ngành hàng.", groups = ShopStep.class)
    private String shopCategory;

    @NotBlank(message = "Vui lòng nhập địa chỉ kho/gian hàng.", groups = ShopStep.class)
    @Size(max = 255, message = "Địa chỉ gian hàng không được vượt quá 255 ký tự.", groups = ShopStep.class)
    private String shopAddress;

    @NotBlank(message = "Vui lòng nhập mã số thuế.", groups = ShopStep.class)
    @Pattern(regexp = "^[0-9]{10,13}$", message = "Mã số thuế phải có 10 đến 13 chữ số.", groups = ShopStep.class)
    private String taxCode;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự.", groups = ShopStep.class)
    private String shopDescription;

    public boolean hasPersonalInformation() {
        return StringUtils.hasText(fullName)
                && StringUtils.hasText(email)
                && StringUtils.hasText(phone)
                && StringUtils.hasText(identityNumber)
                && StringUtils.hasText(contactAddress);
    }

    public boolean hasShopInformation() {
        return StringUtils.hasText(shopName)
                && StringUtils.hasText(shopCategory)
                && StringUtils.hasText(shopAddress)
                && StringUtils.hasText(taxCode);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }
}
