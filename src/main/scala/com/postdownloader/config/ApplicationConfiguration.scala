package com.postdownloader.config
import java.io.IOException

import com.typesafe.config.ConfigException.{Missing, Null}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success, Try}

object ApplicationConfiguration extends LazyLogging {

  private val PostsWritingDirectoryPath: String = "application.directory.downloadPostsToDirectory"

  lazy val PostWritingDirectory: String = Try {
    ConfigFactory
      .load()
      .getString(PostsWritingDirectoryPath)
  } match {
    case Success(path) => path
    case Failure(exception: Null) =>
      handleConfigurationPropertyException(
        "Directory where posts should be saved to files cannot be null. Please provide valid path and try again.",
        exception
      )
    case Failure(exception: Missing) =>
      handleConfigurationPropertyException(
        "Missing path to directory where posts should be saved to files. Please provide valid path and try again.",
        exception
      )
    case Failure(exception) =>
      handleConfigurationPropertyException(
        "Something went wrong during configuration loading",
        exception
      )
  }

  private def handleConfigurationPropertyException(loggingMessage: String, exception: Throwable) = {
    logger.error(loggingMessage)
    throw ConfigurationLoadingException(
      "Missing path to directory where posts should be saved to files. Please provide valid path and try again.",
      exception
    )
  }
}

private case class ConfigurationLoadingException(message: String, cause: Throwable) extends IOException(message, cause)
