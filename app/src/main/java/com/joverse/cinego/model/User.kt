package com.joverse.cinego.model

data class User(
    var Money: Int = 0,
    var Favourite: ArrayList<Film> = ArrayList(),
    var Cart: ArrayList<CartItem> = ArrayList(),
)
