package com.quare.bibleplanner.core.model.plan

data class DayModel(
    val number: Int,
    val books: List<PassagePlanModel>,
    val isRead: Boolean,
)
