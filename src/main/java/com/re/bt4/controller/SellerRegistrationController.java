package com.re.bt4.controller;

import com.re.bt4.dto.SellerRegistrationForm;
import com.re.bt4.entity.SellerRegistration;
import com.re.bt4.service.SellerRegistrationService;
import com.re.bt4.validation.CompleteStep;
import com.re.bt4.validation.PersonalStep;
import com.re.bt4.validation.ShopStep;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/seller/register")
@SessionAttributes(SellerRegistrationController.FORM_ATTRIBUTE)
public class SellerRegistrationController {

    public static final String FORM_ATTRIBUTE = "sellerRegistrationForm";

    private final SellerRegistrationService sellerRegistrationService;

    public SellerRegistrationController(SellerRegistrationService sellerRegistrationService) {
        this.sellerRegistrationService = sellerRegistrationService;
    }

    @ModelAttribute(FORM_ATTRIBUTE)
    public SellerRegistrationForm sellerRegistrationForm() {
        return new SellerRegistrationForm();
    }

    @ModelAttribute("shopCategories")
    public List<String> shopCategories() {
        return List.of("Thời trang", "Điện tử", "Mỹ phẩm", "Gia dụng", "Sách", "Thực phẩm");
    }

    @GetMapping
    public String start() {
        return "redirect:/seller/register/step-1";
    }

    @GetMapping("/step-1")
    public String showPersonalStep() {
        return "register/step-1";
    }

    @PostMapping("/step-1")
    public String submitPersonalStep(
            @Validated(PersonalStep.class) @ModelAttribute(FORM_ATTRIBUTE) SellerRegistrationForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register/step-1";
        }

        return "redirect:/seller/register/step-2";
    }

    @GetMapping("/step-2")
    public String showShopStep(@ModelAttribute(FORM_ATTRIBUTE) SellerRegistrationForm form) {
        if (!form.hasPersonalInformation()) {
            return "redirect:/seller/register/step-1";
        }

        return "register/step-2";
    }

    @PostMapping("/step-2")
    public String submitShopStep(
            @Validated(ShopStep.class) @ModelAttribute(FORM_ATTRIBUTE) SellerRegistrationForm form,
            BindingResult bindingResult) {
        if (!form.hasPersonalInformation()) {
            return "redirect:/seller/register/step-1";
        }

        if (bindingResult.hasErrors()) {
            return "register/step-2";
        }

        return "redirect:/seller/register/step-3";
    }

    @GetMapping("/step-3")
    public String showConfirmStep(@ModelAttribute(FORM_ATTRIBUTE) SellerRegistrationForm form) {
        if (!form.hasPersonalInformation()) {
            return "redirect:/seller/register/step-1";
        }
        if (!form.hasShopInformation()) {
            return "redirect:/seller/register/step-2";
        }

        return "register/confirm";
    }

    @PostMapping("/complete")
    public String complete(
            @Validated(CompleteStep.class) @ModelAttribute(FORM_ATTRIBUTE) SellerRegistrationForm form,
            BindingResult bindingResult,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return chooseStepWithErrors(form);
        }

        SellerRegistration savedRegistration = sellerRegistrationService.save(form);

        // Dọn dữ liệu tạm trong HttpSession sau khi đã lưu DB thành công.
        sessionStatus.setComplete();

        redirectAttributes.addAttribute("id", savedRegistration.getId());
        return "redirect:/seller/register/success";
    }

    @GetMapping("/success")
    public String success() {
        return "register/success";
    }

    private String chooseStepWithErrors(SellerRegistrationForm form) {
        if (!form.hasPersonalInformation()) {
            return "register/step-1";
        }
        if (!form.hasShopInformation()) {
            return "register/step-2";
        }
        return "register/confirm";
    }
}
