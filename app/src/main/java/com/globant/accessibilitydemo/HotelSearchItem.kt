package com.globant.accessibilitydemo

import androidx.annotation.DrawableRes

data class HotelSearchItem(
    val id: Int,
    @DrawableRes val hotelImage: Int,
    val hotelName: String,
    val date: String
)
