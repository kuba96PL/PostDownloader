package com.postdownloader
import com.postdownloader.domain.Post
import spray.json.{DefaultJsonProtocol, JsArray, JsValue, RootJsonFormat, _}

object JSONProtocol extends DefaultJsonProtocol {
  implicit val PostArrayJSONFormat: RootJsonFormat[Array[Post]] = new RootJsonFormat[Array[Post]] {
    override def read(value: JsValue): Array[Post] = value.convertTo[JsArray].elements.map(_.convertTo[Post]).toArray
    override def write(obj: Array[Post]): JsArray = JsArray(obj.map(_.toJson).toVector)
  }
  implicit val SinglePostJSONFormat: RootJsonFormat[Post] = jsonFormat4(Post)
}
