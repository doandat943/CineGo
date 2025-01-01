package com.joverse.cinego.model

data class User(
    var birthdate: String? = null,
    var balance: Int = 0,
    var favourites: ArrayList<Movie> = ArrayList(),
    var tickets: ArrayList<Ticket> = ArrayList(),
)