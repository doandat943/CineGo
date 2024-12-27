package com.joverse.cinego.model

data class User(
    var id: String = "",
    var money: Int = 0,
    var Favourite: ArrayList<Film> = ArrayList(),
    var Cart: ArrayList<CartItem> = ArrayList(),
)