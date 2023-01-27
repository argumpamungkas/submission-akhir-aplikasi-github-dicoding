package com.argumelar.aplikasigithubusernavigationdanapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "user_favorite")
data class UserDetailResponse(

    @SerializedName("avatar_url")
    val avatar: String?,

    @SerializedName("name")
    val name: String?,

    @PrimaryKey (autoGenerate = false)
    @SerializedName("login")
    val username: String,

    @SerializedName("public_repos")
    val repos: Int,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("following")
    val following: Int,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("company")
    val company: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("location")
    val location: String?,

    ):Serializable
