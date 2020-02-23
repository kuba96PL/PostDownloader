package com.postdownloader.http.client
import java.io.IOException

import com.postdownloader.JSONProtocol.PostArrayJSONFormat
import com.postdownloader.config.http.HttpConfiguration.JsonPlaceholderAPIBaseUrl
import com.postdownloader.domain.Post
import com.typesafe.scalalogging.LazyLogging
import sttp.client._
import sttp.client.sprayJson._

import scala.util.{Failure, Success, Try}
class JsonPlaceholderClient(implicit val httpBackend: SttpBackend[Identity, Nothing, NothingT]) extends LazyLogging {

  def fetchAllPosts: Try[List[Post]] = {
    val fetchAllPostsRequest = basicRequest
      .get(uri"$JsonPlaceholderAPIBaseUrl/posts")
      .response(asJson[Array[Post]])

    fetchAllPostsRequest.send().body match {
      case Left(exception) =>
        logger.error("Failed to fetch posts", exception)
        Failure(PostFetchingException("An error occurred while trying to fetch posts", exception))
      case Right(responseBody: Array[Post]) => Success(responseBody.toList)
    }
  }
}

final case class PostFetchingException(message: String, cause: Throwable) extends IOException(message, cause)
