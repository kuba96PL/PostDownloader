package com.postdownloader.config.http
import com.postdownloader.config.handleConfigurationPropertyException
import com.typesafe.config.ConfigException.{Missing, Null}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success, Try}

object HttpConfiguration extends LazyLogging {

  private val DirectoryConfigPath: String = "http.jsonPlaceholder.apiBaseUrl"

  lazy val JsonPlaceholderAPIBaseUrl: String = Try {
    ConfigFactory
      .load()
      .getString(DirectoryConfigPath)
  } match {
    case Success(baseUrl) => baseUrl
    case Failure(exception: Null) =>
      handleConfigurationPropertyException(
        logger,
        "JsonPlaceholder API base URL cannot be null. Please provide valid URL and try again.",
        exception
      )
    case Failure(exception: Missing) =>
      handleConfigurationPropertyException(
        logger,
        "Missing JsonPlaceholder API base URL. Please provide valid URL and try again.",
        exception
      )
    case Failure(exception) =>
      handleConfigurationPropertyException(logger, "Something went wrong during configuration loading", exception)
  }
}
