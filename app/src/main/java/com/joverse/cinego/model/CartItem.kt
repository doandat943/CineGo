package com.joverse.cinego.model

data class CartItem(
    var film: Film? = null,
    var seat: ArrayList<Seat> = ArrayList(),
)
