package com.postdownloader.service

import com.postdownloader.domain.Post
import com.postdownloader.http.client.JsonPlaceholderClient
import com.postdownloader.writer.PostFileWriter

import scala.util.{Failure, Success, Try}

class PostJsonService(httpClient: JsonPlaceholderClient, fileWriter: PostFileWriter) {

  def saveAllPosts: Try[List[Try[Post]]] =
    httpClient.fetchAllPosts match {
      case Success(value)     => Success(fileWriter.writePosts(value))
      case Failure(exception) => Failure(exception)
    }
}
