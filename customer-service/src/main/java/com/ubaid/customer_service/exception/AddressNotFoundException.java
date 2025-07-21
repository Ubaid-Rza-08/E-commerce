package com.ubaid.customer_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddressNotFoundException extends RuntimeException {
    public final String msg;
}
