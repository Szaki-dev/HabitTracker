package com.szaki.habbittracker.data

data class HabitData(
    val name: String,
    val goal: String,
    val done: Boolean = false
)