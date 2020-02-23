package com.postdownloader
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{GivenWhenThen, TryValues}

trait UnitSpec extends AnyFlatSpec with MockFactory with Matchers with GivenWhenThen with TryValues {}
