package com.takeshi.gouda.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var login: String,
    var avatar_url: String,
    var html_url: String
): Parcelable