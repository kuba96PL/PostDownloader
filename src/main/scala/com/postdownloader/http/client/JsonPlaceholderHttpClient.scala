package com.postdownloader.http.client
import java.io.IOException

import com.postdownloader.JSONProtocol.PostArrayJSONFormat
import com.postdownloader.config.http.HttpConfiguration.JsonPlaceholderAPIBaseUrl
import com.postdownloader.http.domain.Post
import com.typesafe.scalalogging.LazyLogging
import sttp.client._
import sttp.client.sprayJson._
class JsonPlaceholderHttpClient(implicit val httpBackend: SttpBackend[Identity, Nothing, NothingT])
    extends LazyLogging {

  def fetchAllPosts: List[Post] = {
    val fetchAllPostsRequest = basicRequest
      .get(uri"$JsonPlaceholderAPIBaseUrl/posts")
      .response(asJson[Array[Post]])

    fetchAllPostsRequest.send().body match {
      case Left(error) =>
        logger.error("Failed to fetch posts")
        throw PostFetchingException("An error occurred while trying to fetch posts", error)
      case Right(responseBody: Array[Post]) => responseBody.toList
    }
  }
}

final case class PostFetchingException(message: String, cause: Throwable) extends IOException(message, cause)
