package com.postdownloader.http.client
import com.postdownloader.JSONProtocol.PostArrayJSONFormat
import com.postdownloader.config.http.HttpConfiguration.JsonPlaceholderAPIBaseUrl
import com.postdownloader.http.domain.Post
import sttp.client._
import sttp.client.sprayJson._

import scala.util.{Failure, Success, Try}
class JsonPlaceholderHttpClient(implicit val httpBackend: SttpBackend[Identity, Nothing, NothingT]) {

  def fetchAllPosts: Try[List[Post]] = {
    val fetchAllPostsRequest = basicRequest
      .get(uri"$JsonPlaceholderAPIBaseUrl/posts")
      .response(asJson[Array[Post]])

    fetchAllPostsRequest.send().body match {
      case Left(error)                      => Failure(error)
      case Right(responseBody: Array[Post]) => Success(responseBody.toList)
    }
  }
}
