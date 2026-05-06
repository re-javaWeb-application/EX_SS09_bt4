# Bài 4 - Form Đăng ký Nhà bán hàng 3 bước

Ứng dụng minh họa wizard form đăng ký nhà bán hàng cho RikkeiMall bằng Spring Boot, Thymeleaf, `@SessionAttributes`, Bean Validation và H2 Database.

## Phần 1 - Phân tích và đề xuất giải pháp

### Giải pháp 1: Dùng `<input type="hidden">` trên Thymeleaf

Ý tưởng: Sau khi người dùng nhập Bước 1, server render Bước 2 và đính kèm dữ liệu Bước 1 vào các input hidden. Khi submit Bước 2, trình duyệt gửi cả dữ liệu Bước 1 và Bước 2 lên server. Sang Bước 3 cũng tiếp tục đẩy toàn bộ dữ liệu qua hidden input.

Ví dụ:

```html
<input type="hidden" th:field="*{fullName}">
<input type="hidden" th:field="*{email}">
<input type="hidden" th:field="*{phone}">
```

Ưu điểm là server không phải giữ dữ liệu tạm trong RAM. Nhược điểm lớn là dữ liệu nằm ở client nên người dùng có thể mở DevTools/F12 và sửa hidden input trước khi submit. Vì vậy server vẫn bắt buộc validate lại toàn bộ dữ liệu ở Bước 3.

### Giải pháp 2: Dùng `@SessionAttributes` trên Controller

Ý tưởng: Controller khai báo một object form trong session. Bước 1 bind dữ liệu cá nhân vào object này. Bước 2 bind tiếp dữ liệu gian hàng vào cùng object. Bước 3 đọc lại object đang nằm trong session, xác nhận, rồi mới lưu database một lần duy nhất.

Code chính:

```java
@Controller
@RequestMapping("/seller/register")
@SessionAttributes("sellerRegistrationForm")
public class SellerRegistrationController {
    @ModelAttribute("sellerRegistrationForm")
    public SellerRegistrationForm sellerRegistrationForm() {
        return new SellerRegistrationForm();
    }
}
```

Sau khi lưu DB thành công phải dọn dữ liệu tạm:

```java
SellerRegistration savedRegistration = sellerRegistrationService.save(form);
sessionStatus.setComplete();
```

Project này còn cấu hình thêm:

```properties
server.servlet.session.timeout=15m
```

Timeout giúp giải phóng session của người dùng bỏ dở giữa chừng.

## Phần 2 - So sánh và lựa chọn

| Tiêu chí | Hidden input Thymeleaf | `@SessionAttributes` Spring |
| --- | --- | --- |
| Khả năng bảo mật | Thấp hơn. Người dùng có thể F12 sửa HTML/hidden input rồi submit. | Tốt hơn. Dữ liệu tạm nằm ở server, người dùng không sửa trực tiếp được bằng F12. |
| Độ phức tạp khi code | Dễ hiểu nhưng dễ rối khi form có nhiều trường vì phải truyền hidden input qua nhiều bước. | Gọn hơn cho wizard nhiều bước vì dùng chung một object form trong session. |
| Rủi ro phình bộ nhớ server | Thấp, vì dữ liệu tạm chủ yếu nằm ở client. | Có rủi ro nếu nhiều người nhập dở rồi bỏ đi, vì dữ liệu nằm trong session server. |
| Cách giảm rủi ro | Validate lại toàn bộ ở server, không tin hidden input. | Gọi `sessionStatus.setComplete()` sau khi lưu DB và đặt session timeout ngắn hợp lý. |

Lựa chọn: dùng `@SessionAttributes` vì an toàn hơn về tính toàn vẹn dữ liệu. Để xử lý bẫy tràn RAM, project có cả cleanup chủ động ở API hoàn tất và timeout cho session bỏ dở.

## Phần 3 - Thiết kế và triển khai

Luồng chạy:

1. `GET /seller/register/step-1`: nhập thông tin cá nhân.
2. `POST /seller/register/step-1`: validate nhóm `PersonalStep`, giữ dữ liệu trong session, chuyển Bước 2.
3. `GET /seller/register/step-2`: nhập thông tin gian hàng.
4. `POST /seller/register/step-2`: validate nhóm `ShopStep`, giữ dữ liệu trong session, chuyển Bước 3.
5. `GET /seller/register/step-3`: hiển thị màn hình xác nhận.
6. `POST /seller/register/complete`: validate toàn bộ, lưu H2 Database, gọi `sessionStatus.setComplete()` để dọn bộ nhớ session.

Các file chính:

- `src/main/java/com/re/bt4/controller/SellerRegistrationController.java`
- `src/main/java/com/re/bt4/dto/SellerRegistrationForm.java`
- `src/main/java/com/re/bt4/entity/SellerRegistration.java`
- `src/main/java/com/re/bt4/service/SellerRegistrationService.java`
- `src/main/resources/templates/register/step-1.html`
- `src/main/resources/templates/register/step-2.html`
- `src/main/resources/templates/register/confirm.html`
- `src/main/resources/application.properties`

Chạy ứng dụng:

```bash
./gradlew bootRun
```

Truy cập:

```text
http://localhost:8080/seller/register/step-1
```

H2 Console:

```text
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:seller_wizard
User: sa
Password: để trống
```
"# EX_SS09_bt4" 
