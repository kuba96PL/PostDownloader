package com.postdownloader
import com.postdownloader.JSONProtocol.SinglePostJSONFormat
import com.postdownloader.http.domain.Post
import org.scalamock.scalatest.MockFactory
import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spray.json._

class JSONProtocolTest extends AnyFlatSpec with MockFactory with Matchers with GivenWhenThen {

  it should "parse single Post JSON to object" in {
    Given("single Post JSON")
    val postJSON = """{ "userId": 1, "id": 1, "title": "lorem ipsum", "body": "dolor sit amet" }"""

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
}
