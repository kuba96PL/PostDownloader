package com.postdownloader.writer
import java.io.IOException

import com.postdownloader.UnitSpec
import com.postdownloader.domain.Post
import com.postdownloader.repository.json.PostJsonFileRepository

import scala.util.{Failure, Success}

class PostFileWriterTest extends UnitSpec {

  private val repository = mock[PostJsonFileRepository]

  private val writer = new PostFileWriter(repository)

  it should "call post repository to save all post" in {
    Given("posts")
    val posts = List(
      Post(1, 1, "title", "body"),
      Post(2, 2, "title_2", "body_2")
    )

    (repository.save _)
        .expects(Post(1, 1, "title", "body"))
        .returning(Success(Post(1, 1, "title", "body")))

    (repository.save _)
      .expects(Post(2, 2, "title_2", "body_2"))
      .returning(Failure(new IOException))

    When("writing posts")
    val result = writer.writePosts(posts)

    Then("return list of tries")
    result.filter(_.isSuccess).head.success shouldEqual Success(Post(1, 1, "title", "body"))
    result.filter(_.isFailure).head.failure.exception.getClass shouldEqual classOf[IOException]
  }
}
