package com.takeshi.gouda.model

data class DetailUser(
    var avatar_url: String,
    var followers: Int,
    var following: Int,
    var public_repos: Int,
    var name: String
)
