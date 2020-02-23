package com.postdownloader
import com.postdownloader.JSONProtocol.{PostArrayJSONFormat, SinglePostJSONFormat}
import com.postdownloader.domain.Post
import spray.json._

class JSONProtocolTest extends UnitSpec {

  it should "parse single Post JSON to object" in {
    Given("single Post JSON")
    val postJSON = """{"userId":1,"id":1,"title":"lorem ipsum","body":"dolor sit amet"}"""

    When("converting JSON to object")
    val result = postJSON.parseJson.convertTo[Post]

    Then("return Post object")
    result shouldEqual Post(
      1,
      1,
      "lorem ipsum",
      "dolor sit amet"
    )
  }

  it should "parse Post to JSON" in {
    Given("Post object")
    val postObject = Post(1, 2, "lorem ipsum", "dolor sit amet")

    When("converting object to JSON")
    val result = postObject.toJson.compactPrint

    Then("return Post as JSON")
    result shouldEqual
      """{"body":"dolor sit amet","id":2,"title":"lorem ipsum","userId":1}"""
  }

  it should "parse Post JSON array to object Array" in {
    Given("Post JSON array")
    val postJSON =
      """[{"userId":1,"id":1,"title":"title_1","body":"body_1"},{"userId":2,"id":2,"title":"title_2","body":"body_2"},{"userId":3,"id":3,"title":"title_3","body":"body_3"}]"""

    When("converting JSON to Array[Post]")
    val result = postJSON.parseJson.convertTo[Array[Post]]

    Then("return Array[Post]")
    result should contain only (
      Post(1, 1, "title_1", "body_1"),
      Post(2, 2, "title_2", "body_2"),
      Post(3, 3, "title_3", "body_3"),
    )
  }

  it should "parse Array[Post] to Post JSON array " in {
    Given("Array[Post]")
    val postArray = Array(
      Post(1, 1, "title_1", "body_1"),
      Post(2, 2, "title_2", "body_2"),
      Post(3, 3, "title_3", "body_3"),
    )
    When("converting JSON to Array[Post]")
    val result = PostArrayJSONFormat.write(postArray).compactPrint

    Then("return Array[Post]")
    result shouldEqual """[{"body":"body_1","id":1,"title":"title_1","userId":1},{"body":"body_2","id":2,"title":"title_2","userId":2},{"body":"body_3","id":3,"title":"title_3","userId":3}]"""
  }
}
