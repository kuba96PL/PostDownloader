package com.postdownloader.domain

final case class Post(
    userId: Int,
    id: Int,
    title: String,
    body: String
)
