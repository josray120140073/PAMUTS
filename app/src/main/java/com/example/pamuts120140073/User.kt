package com.example.pamuts120140073

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val email: String,
    val username: String? = null,
    val first_name: String,
    val last_name: String,
    val avatar: String
): Parcelable