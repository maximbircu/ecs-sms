package com.ecs.sms.features.school.domain.models

import java.time.Instant

data class Enrollment(
    val studentId: String,
    val courseInstanceId: String,
    val paymentStatus: PaymentStatus,
    val joinedAt: Instant
)

enum class PaymentStatus {
    PAID,             // ✅ Full payment received
    RESERVED,         // 🟡 Seat reserved, not yet paid
    INVOICE_SENT,     // 📩 Invoice sent but not paid
    PARTIALLY_PAID,   // 🔁 Deposit or first installment
    CANCELED,         // ❌ Dropped out or didn't pay
    EXEMPTED          // 🆓 Free enrollment (promo, scholarship)
}