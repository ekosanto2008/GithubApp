package com.takeshi.gouda.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var login: String,
    var avatar_url: String,
    var html_url: String
): Parcelable