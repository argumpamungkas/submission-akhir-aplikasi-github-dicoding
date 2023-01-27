package com.argumelar.aplikasigithubusernavigationdanapi.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items")
    val items: List<ItemUsers>,

    @SerializedName("total_count")
    val total_count: Int
)

data class ItemUsers(
    @SerializedName("login")
    val username: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("avatar_url")
    val avatar_url: String
)
