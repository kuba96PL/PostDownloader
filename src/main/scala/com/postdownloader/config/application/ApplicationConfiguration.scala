package com.postdownloader.config.application

import com.postdownloader.config.handleConfigurationPropertyException
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
        logger,
        "Directory where posts should be saved to files cannot be null. Please provide valid path and try again.",
        exception
      )
    case Failure(exception: Missing) =>
      handleConfigurationPropertyException(
        logger,
        "Missing path to directory where posts should be saved to files. Please provide valid path and try again.",
        exception
      )
    case Failure(exception) =>
      handleConfigurationPropertyException(logger, "Something went wrong during configuration loading", exception)
  }

}
