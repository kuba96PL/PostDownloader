package com.postdownloader.config
import java.io.IOException

import com.typesafe.config.ConfigException.{Missing, Null}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success, Try}

object ApplicationConfiguration extends LazyLogging {

  private val DirectoryConfigPath: String = "application.directory.downloadPostsToDirectory"
  lazy val PostWritingDirectory: String = Try {
    ConfigFactory
      .load()
      .getString(DirectoryConfigPath)
  } match {
    case Success(path) => path
    case Failure(exception: Null) =>
      logger.error(
        "Directory where posts should be saved to files cannot be null. Please provide valid path and try again."
      )
      throw ConfigurationLoadingException("Missing path to directory where posts should be saved to files. Please provide valid path and try again.", exception)
    case Failure(exception: Missing) =>
      logger.error(
        "Missing path to directory where posts should be saved to files. Please provide valid path and try again."
      )
      throw ConfigurationLoadingException("Missing path to directory where posts should be saved to files.", exception)
    case Failure(exception) =>
      logger.error("Something went wrong during configuration loading: ")
      throw exception
  }
}

private case class ConfigurationLoadingException(message: String, cause: Throwable) extends IOException(message, cause)
