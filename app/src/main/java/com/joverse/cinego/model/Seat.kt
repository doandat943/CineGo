package com.joverse.cinego.model

data class Seat(var status: SeatStatus, var name: String) {

    enum class SeatStatus {
        AVAILABLE, SELECTED, UNAVAILABLE
    }
}
