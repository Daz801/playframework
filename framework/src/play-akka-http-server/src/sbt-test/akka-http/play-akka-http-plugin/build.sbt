//
// Copyright (C) 2009-2016 Lightbend Inc. <https://www.lightbend.com>
//

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, PlayAkkaHttpServer)
  .disablePlugins(PlayNettyServer)

name := "compiled-class"

scalaVersion := Option(System.getProperty("scala.version")).getOrElse("2.12.0")

// Change our tests directory because the usual "test" directory clashes
// with the scripted "test" file.
scalaSource in Test := (baseDirectory.value / "tests")

libraryDependencies ++= Seq(
  guice,
  ws,
  specs2 % Test
)

PlayKeys.playInteractionMode := play.sbt.StaticPlayNonBlockingInteractionMode

InputKey[Unit]("verifyResourceContains") := {
  val args = Def.spaceDelimited("<path> <status> <words> ...").parsed
  val path = args.head
  val status = args.tail.head.toInt
  val assertions = args.tail.tail
  DevModeBuild.verifyResourceContains(path, status, assertions, 0)
}
