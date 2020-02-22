package com.postdownloader.http.domain

final case class Post(
    userId: Int,
    id: Int,
    title: String,
    body: String
)
