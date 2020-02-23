package com.postdownloader
import java.io.IOException

import com.typesafe.scalalogging.Logger

package object config {

  final case class ConfigurationLoadingException(message: String, cause: Throwable) extends IOException(message, cause)

  @throws[ConfigurationLoadingException]
  def handleConfigurationPropertyException(
      logger: Logger,
      loggingMessage: String,
      exception: Throwable
  ): Nothing = {
    logger.error(loggingMessage)
    throw ConfigurationLoadingException(
      "Missing path to directory where posts should be saved to files. Please provide valid path and try again.",
      exception
    )
  }
}
