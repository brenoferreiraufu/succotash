package br.ufu.succotash.mock;

import java.math.BigDecimal;

public record MockPaymentRequest(BigDecimal total, String orderId) {}
