name := "sprinter"
organization := "falalafel"
version := "0.1"
scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= {

  val slickVersion     = "3.3.0"
  val akkaVersion      = "2.5.20"
  val akkaHttpVersion  = "10.1.7"
  val scalaTestVersion = "3.0.7"
  val circeVersion     = "0.11.1"
  val macwireVersion   = "2.3.1"
  val akkaCirceVersion = "1.25.2"
  val postgresVersion  = "42.2.5"
  val slf4jVersion     = "1.7.25"
  val catsVersion      = "1.5.0"
  val catsSlickVersion = "0.9.1"

  Seq(
    "com.typesafe.slick"       %% "slick"           % slickVersion,
    "com.typesafe.slick"       %% "slick-hikaricp"  % slickVersion,
    "com.typesafe.akka"        %% "akka-http"       % akkaHttpVersion,
    "com.typesafe.akka"        %% "akka-stream"     % akkaVersion,
    "com.typesafe.akka"        %% "akka-actor"      % akkaVersion,
    "org.scalatest"            %% "scalatest"       % scalaTestVersion % "test,it",
    "com.softwaremill.macwire" %% "macros"          % macwireVersion,
    "io.circe"                 %% "circe-core"      % circeVersion,
    "io.circe"                 %% "circe-generic"   % circeVersion,
    "io.circe"                 %% "circe-parser"    % circeVersion,
    "io.circe"                 %% "circe-generic-extras" % circeVersion,
    "de.heikoseeberger"        %% "akka-http-circe" % akkaCirceVersion,
    "org.postgresql"           % "postgresql"       % postgresVersion,
    "org.slf4j"                % "slf4j-nop"        % slf4jVersion,
    "org.typelevel"            %% "cats-core"       % catsVersion,
    "com.rms.miu"              %% "slick-cats"      % catsSlickVersion,
    "com.typesafe.akka"        %% "akka-stream-testkit" % akkaVersion,
    "com.typesafe.akka"        %% "akka-http-testkit"   % akkaHttpVersion
  )

}

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings:  _*
    // other settings here
  )
