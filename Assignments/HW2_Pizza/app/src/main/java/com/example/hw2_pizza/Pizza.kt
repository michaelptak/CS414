package com.example.hw2_pizza

import java.io.Serializable

class Pizza(
    val type: String,
    val price: Double,
    val toppingCount: Int,
    val size: String,
    val imageId: Int
) : Serializable