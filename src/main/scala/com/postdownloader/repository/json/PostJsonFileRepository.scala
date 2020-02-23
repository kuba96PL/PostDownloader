package com.postdownloader.repository.json
import java.io.{BufferedWriter, File, FileWriter, IOException}

import com.postdownloader.JSONProtocol.SinglePostJSONFormat
import com.postdownloader.config.application.ApplicationConfiguration.PostWritingDirectory
import com.postdownloader.domain.Post
import com.postdownloader.repository.PostRepository
import com.typesafe.scalalogging.LazyLogging
import spray.json._

import scala.util.{Failure, Success, Try}

class PostJsonFileRepository extends PostRepository with LazyLogging {
  override def save(post: Post): Try[Post] = {
    val file: File = createFileWithDirectories(post)
    val bufferedWriter = new BufferedWriter(new FileWriter(file))

    Try(bufferedWriter.write(post.toJson.prettyPrint)) match {
      case Success(_: Unit) =>
        bufferedWriter.close()
        Success(post)
      case Failure(exception) =>
        logger.error(s"Failed to write post to file: POST ID - ${post.id}")
        bufferedWriter.close()
        Failure(FailedToWritePostToFileException(post, exception))
    }
  }
  private def createFileWithDirectories(post: Post) = {
    val file = new File(s"$PostWritingDirectory/${post.id}.json")
    file.getParentFile.mkdirs()
    file
  }
}

final private case class FailedToWritePostToFileException(post: Post, cause: Throwable)
    extends IOException(
      s"Failed to write post to file. Post ID: ${post.id}",
      cause
    )
