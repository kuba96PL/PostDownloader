package com.postdownloader.repository
import com.postdownloader.http.domain.Post

import scala.util.Try

trait PostRepository {

  def save(post: Post): Try[Post]
}
