package com.postdownloader.http.client
import com.postdownloader.JSONProtocol.PostArrayJSONFormat
import com.postdownloader.http.client.JsonPlaceholderHttpClient.GetAllPostsUri
import com.postdownloader.http.domain.Post
import sttp.client._
import sttp.client.sprayJson._

import scala.util.{Failure, Success, Try}
class JsonPlaceholderHttpClient {

  private implicit val backend: SttpBackend[Identity, Nothing, NothingT] = HttpURLConnectionBackend()

  def fetchAllPosts: Try[Array[Post]] = {
    val fetchAllPostsRequest = basicRequest
      .get(GetAllPostsUri)
      .response(asJson[Array[Post]])

    fetchAllPostsRequest.send().body match {
      case Left(error)                      => Failure(error)
      case Right(responseBody: Array[Post]) => Success(responseBody)
    }
  }

}

private object JsonPlaceholderHttpClient {
  private val GetAllPostsUri = uri"https://jsonplaceholder.typicode.com/posts"
}
