package com.app.adidas.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Product(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("price")
    val price: BigDecimal,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("reviews")
    val reviews: List<Review>,
) : Parcelable {

    @Parcelize
    data class Review(
        @SerializedName("productId")
        val productId: String,
        @SerializedName("locale")
        val locale: String,
        @SerializedName("rating")
        val rating: Int,
        @SerializedName("text")
        val text: String,
    ) : Parcelable

    fun getPriceWithCurrency() = currency.plus(price.toString())
}