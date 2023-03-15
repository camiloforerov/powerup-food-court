package com.pragma.powerup.domain;

public class Constants {
    public static final String ORDER_PENDING_STATE = "PENDING";
    public static final String ORDER_PREPARATION_STATE = "PREPARATION";
    public static final String ORDER_CANCELED_STATE = "CANCELED";
    public static final String ORDER_READY_STATE = "READY";
    public static final String ORDER_DELIVERED_STATE = "DELIVERED";

    public static final String NOTIFICATION_MESSAGE = "Hola, tu pedido está listo! Dale este código " +
            "de verificación al empleado para recibir tu pedido: ";
    public static final String CANCEL_ORDER_ERROR = "Lo sentimos, tu pedido ya está en preparación " +
            "y no puede cancelarse";
}
