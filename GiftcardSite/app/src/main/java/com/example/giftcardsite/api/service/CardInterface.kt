package com.example.giftcardsite.api.service

import com.example.giftcardsite.api.model.BuyCardInfo
import com.example.giftcardsite.api.model.Card
import retrofit2.Call
import retrofit2.http.*

public interface CardInterface {
    @PUT("/api/use/{card_number}") // Part 4 vulnerability where PUT request can be tampered with or otherwise forged which would manipulate function of UseCard.kt, its coorisponding file
    fun useCard(@Path("card_number") card_number: Int?, @Header("Authorization") authHeader: String): Call<Card?>

    @GET("/api/cards")
    fun getCards(@Header("Authorization") authHeader: String): Call<List<Card?>?>

    @POST("api/buy/{product_number}")
    fun buyCard(@Path("product_number") product_number: Int?, @Body buyCardInfo: BuyCardInfo, @Header("Authorization") authHeader: String): Call<Card?>?
}