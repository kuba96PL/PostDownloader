package com.postdownloader
import org.scalamock.scalatest.MockFactory
import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

trait UnitSpec extends AnyFlatSpec with MockFactory with Matchers with GivenWhenThen {}
