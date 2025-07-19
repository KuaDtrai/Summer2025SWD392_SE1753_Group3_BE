package com.project.ibtss.enums;

import lombok.AccessLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    RUNTIME_EXCEPTION(999, "Không xác định!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(100, "Không bắt được lỗi!", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(101, "Refresh token không hợp lệ!", HttpStatus.UNAUTHORIZED),
    INVALID_AUTHORIZATION_HEADER(102, "Xác thực không hợp lệ!", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_UNKNOWN_ERROR(103, "Refresh token hết hạn hoặc không hợp lệ!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_CLIENT(104, "Người dùng không được phép!", HttpStatus.UNAUTHORIZED),

    ACCOUNT_NOT_FOUND(200, "Không tìm thấy tài khoản!", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(201, "Không tìm thấy người dùng!", HttpStatus.NOT_FOUND),
    INCORRECT_PASSWORD(202, "Sai mật khẩu!", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(203, "Mật khẩu và xác nhận mật khẩu không khớp!", HttpStatus.BAD_REQUEST),
    PHONE_EXISTED(204, "Số điện thoại đã tồn tại!", HttpStatus.BAD_REQUEST),
    STAFF_NOT_FOUND(205, "Không tìm thấy nhân viên!", HttpStatus.NOT_FOUND),
    ACCOUNT_INACTIVE(206, "Tài khoản không hoạt động!", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(207, "Email đã tồn tại!", HttpStatus.BAD_REQUEST),

    CONFLICTING_TRIP(301, "Xe bus hoặc tài xế đã được phân công cho một chuyến nào đó ở thời điểm này!", HttpStatus.CONFLICT),
    INVALID_TIME_RANGE(304, "Thời gian khởi hành phải nhỏ hơn thời gian đến!", HttpStatus.BAD_REQUEST),
    BUS_INACTIVE(302, "Xe buýt đang không hoạt động!", HttpStatus.BAD_REQUEST),
    INVALID_DRIVER(303, "Tài xế không hợp lệ!", HttpStatus.BAD_REQUEST),
    CANNOT_MODIFY_PAST_TRIP(305, "Không thể sửa chuyến đã bắt đầu hoặc đã kết thúc!", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_TRIP_WITH_TICKETS(306, "Không thể xoá chuyến có vé đã được bán!", HttpStatus.BAD_REQUEST),
    FEEDBACK_NOT_FOUND(307, "Không tìm thấy phản hồi", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND(308, "Không tìm thấy khách hàng", HttpStatus.NOT_FOUND ),

    BUS_EXISTED(400, "Xe buýt đã tồn tại!", HttpStatus.BAD_REQUEST),
    BUS_NOT_EXISTED(401, "Xe buýt không tồn tại!", HttpStatus.BAD_REQUEST),
    BUS_NOT_ACTIVE(402, "Xe buýt chưa được kích hoạt!", HttpStatus.BAD_REQUEST),
    BUS_ALREADY_ASSIGNED_TO_TRIP(403, "Xe buýt đã được phân công cho chuyến khác!", HttpStatus.BAD_REQUEST),
    CANT_EDIT_BUS(404, "Không thể chỉnh sửa xe buýt lúc này!", HttpStatus.BAD_REQUEST),

    TRIP_NOT_EXISTED(500, "Chuyến xe không tồn tại!", HttpStatus.BAD_REQUEST),
    CANNOT_EDIT_TRIP(501, "Không thể chỉnh sửa chuyến xe này!", HttpStatus.BAD_REQUEST),
    HAVE_USED_TICKET(502, "Chuyến xe đã có vé được dùng!", HttpStatus.BAD_REQUEST),

    SEAT_NOT_EXISTED(600, "Ghế ngồi không tồn tại!", HttpStatus.BAD_REQUEST),
    SEAT_NOT_AVAILABLE(601, "Ghế không còn trống!", HttpStatus.BAD_REQUEST),

    CUSTOMER_NOT_EXISTED(700, "Khách hàng không tồn tại!", HttpStatus.BAD_REQUEST),

    ROUTE_STATION_NOT_EXISTED(800, "Trạm trên tuyến không tồn tại!", HttpStatus.BAD_REQUEST),

    PAYMENT_NOT_EXISTED(900, "Thanh toán không tồn tại!", HttpStatus.BAD_REQUEST),

    TICKET_PAY_NOT_EXISTED(1000, "Thanh toán cho vé không tồn tại!", HttpStatus.BAD_REQUEST),

    TICKET_NOT_EXISTED(1100, "Vé không tồn tại!", HttpStatus.BAD_REQUEST),
    TICKET_CAN_NOT_CANCEL(1101, "Không thể hủy vé!", HttpStatus.BAD_REQUEST),
    TICKET_IS_USED_OR_CANCELLED(1012, "Vé đã được sử dụng hoặc đã bị hủy!", HttpStatus.BAD_REQUEST ),

    STATION_NOT_EXISTED(1200, "Trạm không tồn tại!", HttpStatus.BAD_REQUEST),

    TICKET_SEGMENT_NOT_EXISTED(1300, "Chặng vé không tồn tại!", HttpStatus.BAD_REQUEST),

    ROUTE_NOT_EXISTED(1400, "Tuyến xe không tồn tại!", HttpStatus.BAD_REQUEST),
    CANT_EDIT_ROUTE(1401, "Không thể chỉnh sửa tuyến xe!", HttpStatus.BAD_REQUEST),

    CANT_CHANGE_TICKET(1500, "Không thể thay đổi vé!", HttpStatus.BAD_REQUEST),

    NOT_STAFF_ROLE(1600, "Bạn phải chọn nhân viên tài xế!", HttpStatus.BAD_REQUEST),
    NOT_DRIVER_POSITON(1601, "Nhân viên này không phải tài xế!", HttpStatus.BAD_REQUEST),
    DRIVER_ALREADY_ASSIGNED_TO_TRIP(1602, "Tài xế đã được phân công cho chuyến khác", HttpStatus.BAD_REQUEST),

    ;
    int code;
    String message;
    HttpStatusCode statusCode;
}
