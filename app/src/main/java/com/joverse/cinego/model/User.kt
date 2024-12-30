package com.joverse.cinego.model

data class User(
    var birthdate: String? = null,
    var balance: Int = 0,
    var favourites: ArrayList<Film> = ArrayList(),
    var cart: ArrayList<CartItem> = ArrayList(),
)