package com.postdownloader.service

import com.postdownloader.http.client.JsonPlaceholderHttpClient
import com.postdownloader.http.domain.Post
import com.postdownloader.writer.PostFileWriter
import org.scalamock.scalatest.MockFactory
import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

class PostServiceTest extends AnyFlatSpec with MockFactory with Matchers with GivenWhenThen {

  private val httpClient: JsonPlaceholderHttpClient = mock[JsonPlaceholderHttpClient]
  private val fileWriter: PostFileWriter = mock[PostFileWriter]

  private val postService: PostService = new PostService(
    httpClient,
    fileWriter
  )

  it should "fetch posts and save them to files" in {
    Given("posts fetched by HTTP client and saved by file writer")
    (httpClient.fetchAllPosts _)
      .expects()
      .returning(
        Success(
          List(
            Post(1, 1, "title", "body"),
            Post(2, 2, "title_2", "body_2")
          )
        )
      )
    (fileWriter.writePosts _)
      .expects(
        List(
          Post(1, 1, "title", "body"),
          Post(2, 2, "title_2", "body_2")
        )
      )
      .returning(
        List(
          Success(Post(1, 1, "title", "body")),
          Success(Post(2, 2, "title_2", "body_2"))
        )
      )

    When("fetching and saving posts")
    val result = postService.fetchAndSave

    Then("return list of fetched and saved posts")
    result should contain only (
      Success(Post(1, 1, "title", "body")),
      Success(Post(2, 2, "title_2", "body_2"))
    )
  }
}
