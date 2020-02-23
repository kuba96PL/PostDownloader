package com.postdownloader.writer
import com.postdownloader.http.domain.Post
import com.postdownloader.repository.PostRepository
import com.typesafe.scalalogging.LazyLogging

import scala.util.Try

class PostFileWriter(postRepository: PostRepository) extends LazyLogging {

  def writePosts(posts: List[Post]): List[Try[Post]] = posts.map(postRepository.save)
}
