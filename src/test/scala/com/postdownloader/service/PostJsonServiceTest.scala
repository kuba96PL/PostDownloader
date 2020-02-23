package com.postdownloader.service

import java.io.IOException

import com.postdownloader.domain.Post
import com.postdownloader.http.client.JsonPlaceholderClient
import com.postdownloader.writer.PostFileWriter
import org.scalamock.scalatest.MockFactory
import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

class PostJsonServiceTest extends AnyFlatSpec with MockFactory with Matchers with GivenWhenThen {

  private val httpClient: JsonPlaceholderClient = mock[JsonPlaceholderClient]
  private val fileWriter: PostFileWriter = mock[PostFileWriter]

  private val postService: PostJsonService = new PostJsonService(
    httpClient,
    fileWriter
  )

  it should "fetch posts and save them to files" in {
    Given("posts fetched by HTTP client and saved by file writer")
    val post1 = Post(1, 1, "title", "body")
    val post2 = Post(2, 2, "title_2", "body_2")

    (httpClient.fetchAllPosts _)
      .expects()
      .returning(Success(List(post1, post2)))

    (fileWriter.writePosts _)
      .expects(List(post1, post2))
      .returning(List(Success(post1), Success(post2)))

    When("fetching and saving posts")
    val result = postService.saveAllPosts

    Then("return list of fetched and saved posts")
    result shouldEqual Success(List(Success(post1), Success(post2)))
  }

  it should "throw PostFetchingException" in {
    Given("Failure during posts fetching")
    (httpClient.fetchAllPosts _)
      .expects()
      .throwing(new IOException(new RuntimeException("some exception thrown")))

    When("fetching and saving posts")
    val result = the[IOException] thrownBy postService.saveAllPosts

    Then("throw PostFetchingException")
    result.getCause.getClass shouldEqual classOf[RuntimeException]
    result.getCause.getMessage shouldEqual "some exception thrown"
  }
}
