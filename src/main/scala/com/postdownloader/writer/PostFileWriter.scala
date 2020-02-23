package com.postdownloader.writer
import java.io.{BufferedWriter, File, FileWriter, IOException}

import com.postdownloader.JSONProtocol.SinglePostJSONFormat
import com.postdownloader.config.application.ApplicationConfiguration.PostWritingDirectory
import com.postdownloader.http.domain.Post
import com.typesafe.scalalogging.LazyLogging
import spray.json._

import scala.util.{Failure, Success, Try}

class PostFileWriter extends LazyLogging {

  def writePosts(posts: List[Post]): List[Try[Post]] =
    posts
      .map(
        post =>
          writePostToFile(
            filename = s"$PostWritingDirectory/${post.id}.json",
            postToWrite = post
        )
      )

  private def writePostToFile(filename: String, postToWrite: Post): Try[Post] = {
    val file = new File(filename)
    val bufferedWriter = new BufferedWriter(new FileWriter(file))

    Try {
      bufferedWriter.write(postToWrite.toJson.prettyPrint)
    } match {
      case Success(_: Unit) =>
        bufferedWriter.close()
        Success(postToWrite)
      case Failure(exception) =>
        logger.error(s"Failed to write post to file: POST ID - ${postToWrite.id}")
        bufferedWriter.close()
        Failure(FailedToWritePostToFileException(postToWrite, exception))
    }
  }
}

case class FailedToWritePostToFileException(post: Post, cause: Throwable)
    extends IOException(
      s"Failed to write post to file. Post ID: ${post.id}",
      cause
    )
