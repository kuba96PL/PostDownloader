package com.postdownloader.http.client
import com.postdownloader.UnitSpec
import com.postdownloader.config.http.HttpConfiguration.JsonPlaceholderAPIBaseUrl
import com.postdownloader.domain.Post
import sttp.client._
import sttp.client.testing.SttpBackendStub
import sttp.model.StatusCode.Ok

import scala.util.Success

class JsonPlaceholderClientTest extends UnitSpec {

  it should "return Success with List of posts" in {
    Given("JsonPlaceholderClient")
    implicit val testingBackend: SttpBackendStub[Identity, Nothing] = SttpBackendStub.synchronous
      .whenRequestMatches(_.uri == uri"$JsonPlaceholderAPIBaseUrl/posts")
      .thenRespond(
        Response(
          Right(
            Array(
              Post(1, 1, "title_1", "body_1"),
              Post(2, 2, "title_2", "body_2"),
              Post(3, 3, "title_3", "body_3")
            )
          ),
          Ok
        )
      )

    val client = new JsonPlaceholderClient()

    When("fetching all posts")
    val result = client.fetchAllPosts

    Then("return posts as List")
    result shouldEqual Success(
      List(
        Post(1, 1, "title_1", "body_1"),
        Post(2, 2, "title_2", "body_2"),
        Post(3, 3, "title_3", "body_3")
      )
    )
  }

  it should "return Failure with exception" in {
    Given("JsonPlaceholderClient")
    implicit val testingBackend: SttpBackendStub[Identity, Nothing] = SttpBackendStub.synchronous
      .whenRequestMatches(_.uri == uri"$JsonPlaceholderAPIBaseUrl/posts")
      .thenRespondServerError()

    val client = new JsonPlaceholderClient()

    When("fetching all posts")
    val result = client.fetchAllPosts

    Then("return Failure")
    result.failure.exception.getMessage shouldEqual "An error occurred while trying to fetch posts"
  }
}
