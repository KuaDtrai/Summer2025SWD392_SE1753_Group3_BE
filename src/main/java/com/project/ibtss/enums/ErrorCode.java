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
    RUNTIME_EXCEPTION(999, "Uncaught exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(100, "Uncaught exception", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(101, "Invalid refresh token", HttpStatus.UNAUTHORIZED),
    INVALID_AUTHORIZATION_HEADER(102, "Invalid authorization header", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_UNKNOWN_ERROR(103, "Refresh token expired or invalid", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_CLIENT(104, "Unauthorized client", HttpStatus.UNAUTHORIZED),

    ACCOUNT_NOT_FOUND(200, "Account not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(201, "User not found", HttpStatus.NOT_FOUND),
    INCORRECT_PASSWORD(202, "Incorrect password", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(203, "Password and Confirm Password do not match", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(204, "Username already exists", HttpStatus.BAD_REQUEST),
    STAFF_NOT_FOUND(205, "Staff not found", HttpStatus.NOT_FOUND),

    CONFLICTING_TRIP(301, "Bus or driver is already assigned to another trip during this time", HttpStatus.CONFLICT),
    INVALID_TIME_RANGE(304, "Departure time must be before arrival time", HttpStatus.BAD_REQUEST),
    BUS_INACTIVE(302, "Bus is inactive", HttpStatus.BAD_REQUEST),
    INVALID_DRIVER(303, "Invalid driver", HttpStatus.BAD_REQUEST),
    CANNOT_MODIFY_PAST_TRIP(305, "Cannot modify a trip that has already started or ended", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_TRIP_WITH_TICKETS(306, "Cannot delete a trip that has tickets sold", HttpStatus.BAD_REQUEST),


    BUS_EXISTED(400, "Bus already exists", HttpStatus.BAD_REQUEST),
    BUS_NOT_EXISTED(401, "Bus does not exist", HttpStatus.BAD_REQUEST),
    BUS_NOT_ACTIVE(402, "Bus is not active", HttpStatus.BAD_REQUEST),
    BUS_ALREADY_ASSIGNED_TO_TRIP(403, "Bus already assigned to a trip", HttpStatus.BAD_REQUEST),

    TRIP_NOT_EXISTED(500, "Trip does not exist", HttpStatus.BAD_REQUEST),
    CANNOT_EDIT_TRIP(501, "Can't edit a trip", HttpStatus.BAD_REQUEST),

    SEAT_NOT_EXISTED(600, "Seat does not exist", HttpStatus.BAD_REQUEST),
    SEAT_NOT_AVAILABLE(601, "Seat is not available", HttpStatus.BAD_REQUEST),

    CUSTOMER_NOT_EXISTED(700, "Customer does not exist", HttpStatus.BAD_REQUEST),

    ROUTE_STATION_NOT_EXISTED(800, "Route station does not exist", HttpStatus.BAD_REQUEST),

    PAYMENT_NOT_EXISTED(900, "Payment does not exist", HttpStatus.BAD_REQUEST),

    TICKET_PAY_NOT_EXISTED(1000, "Ticket payment does not exist", HttpStatus.BAD_REQUEST),

    TICKET_NOT_EXISTED(1100, "Ticket does not exist", HttpStatus.BAD_REQUEST),
    TICKET_CAN_NOT_CANCEL(1101, "Can not cancel ticket", HttpStatus.BAD_REQUEST),
    TICKET_IS_USED_OR_CANCELLED(1012, "Ticket is already used or cancelled", HttpStatus.BAD_REQUEST ),

    STATION_NOT_EXISTED(1200, "Station does not exist", HttpStatus.BAD_REQUEST),

    TICKET_SEGMENT_NOT_EXISTED(1300, "Ticket segment does not exist", HttpStatus.BAD_REQUEST),

    ROUTE_NOT_EXISTED(1400, "Route does not exist", HttpStatus.BAD_REQUEST),

    CANT_CHANGE_TICKET(1500, "Can not change ticket!", HttpStatus.BAD_REQUEST),

    NOT_STAFF_ROLE(1600, "You must select driver!", HttpStatus.BAD_REQUEST),
    NOT_DRIVER_POSITON(1601, "Staff not driver position!", HttpStatus.BAD_REQUEST),
    DRIVER_ALREADY_ASSIGNED_TO_TRIP(1602, "Driver already assigned to a trip", HttpStatus.BAD_REQUEST),
    ;
    FEEDBACK_NOT_FOUND(307, "Feedback not found", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND(308, "Customer not found", HttpStatus.NOT_FOUND ),;
    int code;
    String message;
    HttpStatusCode statusCode;
}
