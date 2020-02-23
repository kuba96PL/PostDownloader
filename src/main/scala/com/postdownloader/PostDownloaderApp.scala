package com.postdownloader

import com.postdownloader.http.client.JsonPlaceholderHttpClient
import com.postdownloader.repository.json.PostJsonFileRepository
import com.postdownloader.service.PostService
import com.postdownloader.writer.PostFileWriter
import sttp.client.{HttpURLConnectionBackend, Identity, NothingT, SttpBackend}

object PostDownloaderApp extends App {
  implicit private val httpBackend: SttpBackend[Identity, Nothing, NothingT] = HttpURLConnectionBackend()

  private val httpClient = new JsonPlaceholderHttpClient
  private val fileWriter = new PostFileWriter(new PostJsonFileRepository)

  private val service = new PostService(httpClient, fileWriter)

  service.fetchAndSave
}
