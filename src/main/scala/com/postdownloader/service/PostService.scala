package com.postdownloader.service

import com.postdownloader.domain.Post
import com.postdownloader.http.client.JsonPlaceholderHttpClient
import com.postdownloader.writer.PostFileWriter

import scala.util.Try

class PostService(httpClient: JsonPlaceholderHttpClient, fileWriter: PostFileWriter) {

  def saveAllPosts: List[Try[Post]] = fileWriter.writePosts(httpClient.fetchAllPosts)
}
