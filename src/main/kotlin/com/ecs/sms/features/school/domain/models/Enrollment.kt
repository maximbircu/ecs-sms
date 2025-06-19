package com.ecs.sms.features.school.domain.models

import java.util.*

data class Enrollment(
    val student: Student,
//    val course: Course,
    val paymentStatus: PaymentStatus,
    val id: String = UUID.randomUUID().toString()
)

enum class PaymentStatus {
    PAID,             // ✅ Full payment received
    RESERVED,         // 🟡 Seat reserved, not yet paid
    INVOICE_SENT,     // 📩 Invoice sent but not paid
    PARTIALLY_PAID,   // 🔁 Deposit or first installment
    CANCELED,         // ❌ Dropped out or didn't pay
    EXEMPTED          // 🆓 Free enrollment (promo, scholarship)
}