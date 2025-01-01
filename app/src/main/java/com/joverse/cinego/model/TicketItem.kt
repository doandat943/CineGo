package com.joverse.cinego.model

data class TicketItem(
    var film: Film? = null,
    var seat: ArrayList<Seat> = ArrayList(),
)
