package com.postdownloader.service

import java.io.IOException

import com.postdownloader.http.client.JsonPlaceholderHttpClient
import com.postdownloader.http.domain.Post
import com.postdownloader.writer.PostFileWriter
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success, Try}

class PostService(httpClient: JsonPlaceholderHttpClient, fileWriter: PostFileWriter) {

  def fetchAndSave: List[Try[Post]] = fileWriter.writePosts(fetchPosts)
  private def fetchPosts: List[Post] =
    httpClient.fetchAllPosts match {
      case Success(posts)     => posts
      case Failure(exception) => throw PostFetchingException("An error occurred while fetching posts", exception)
    }
}

case class PostFetchingException(message: String, cause: Throwable) extends IOException(message, cause)
