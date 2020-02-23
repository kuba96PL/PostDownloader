package com.postdownloader.repository
import com.postdownloader.domain.Post

import scala.util.Try

trait PostRepository {

  def save(post: Post): Try[Post]
}
