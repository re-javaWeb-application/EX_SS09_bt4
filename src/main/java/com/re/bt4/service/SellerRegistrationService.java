package com.re.bt4.service;

import com.re.bt4.dto.SellerRegistrationForm;
import com.re.bt4.entity.SellerRegistration;
import com.re.bt4.repository.SellerRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerRegistrationService {

    private final SellerRegistrationRepository sellerRegistrationRepository;

    public SellerRegistrationService(SellerRegistrationRepository sellerRegistrationRepository) {
        this.sellerRegistrationRepository = sellerRegistrationRepository;
    }

    @Transactional
    public SellerRegistration save(SellerRegistrationForm form) {
        SellerRegistration registration = new SellerRegistration(
                form.getFullName(),
                form.getEmail(),
                form.getPhone(),
                form.getIdentityNumber(),
                form.getContactAddress(),
                form.getShopName(),
                form.getShopCategory(),
                form.getShopAddress(),
                form.getTaxCode(),
                form.getShopDescription());

        return sellerRegistrationRepository.save(registration);
    }
}
