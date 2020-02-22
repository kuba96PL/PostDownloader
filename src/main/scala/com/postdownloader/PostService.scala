package com.postdownloader
import com.postdownloader.http.client.JsonPlaceholderHttpClient
import com.postdownloader.http.domain.Post
import com.postdownloader.writer.PostFileWriter
import com.typesafe.scalalogging.LazyLogging

import scala.util.Try

class PostService(httpClient: JsonPlaceholderHttpClient, fileWriter: PostFileWriter) extends LazyLogging {

  def fetchAndSave: Seq[Try[Post]] = {
    val fetchedPosts =
      httpClient.fetchAllPosts
        .getOrElse {
          logger.error("Error occurred while fetching posts from external API")
          throw new RuntimeException()
        }

    fileWriter.writePosts(fetchedPosts.toIndexedSeq)
  }
}
