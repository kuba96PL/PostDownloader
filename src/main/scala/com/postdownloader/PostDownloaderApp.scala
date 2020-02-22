package com.postdownloader

import com.postdownloader.http.client.JsonPlaceholderHttpClient
import com.postdownloader.writer.PostFileWriter

object PostDownloaderApp extends App {

  private val service = new PostService(new JsonPlaceholderHttpClient, new PostFileWriter)

  service.fetchAndSave
}
