package com.globant.accessibilitydemo

import androidx.annotation.DrawableRes

data class HotelSearchItem(
    @DrawableRes val hotelImage: Int,
    val hotelName: String,
    val date: String
)
