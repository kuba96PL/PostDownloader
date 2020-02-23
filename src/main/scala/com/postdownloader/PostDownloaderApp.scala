package com.postdownloader

import com.postdownloader.http.client.JsonPlaceholderClient
import com.postdownloader.repository.json.PostJsonFileRepository
import com.postdownloader.service.PostJsonService
import com.postdownloader.writer.PostFileWriter
import com.typesafe.scalalogging.LazyLogging
import sttp.client.{HttpURLConnectionBackend, Identity, NothingT, SttpBackend}

import scala.util.{Failure, Success}

object PostDownloaderApp extends App with LazyLogging {
  implicit private val httpBackend: SttpBackend[Identity, Nothing, NothingT] = HttpURLConnectionBackend()

  private val httpClient = new JsonPlaceholderClient
  private val fileWriter = new PostFileWriter(new PostJsonFileRepository)

  private val service = new PostJsonService(httpClient, fileWriter)

  service.saveAllPosts match {
    case Success(savedPosts) =>
      val (successSaves, failedSaves) = savedPosts.partition(_.isSuccess)
      logger.info(
        s"Successfuly saved ${successSaves.length} posts. Failed to save ${failedSaves.length} posts"
      )
    case Failure(savingPostsException) =>
      logger.error("An error has occurred during posts saving", savingPostsException)
  }
}
