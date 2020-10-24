// Enable the Lightbend Telemetry (Cinnamon) sbt plugin
lazy val app = project in file(".") enablePlugins(Cinnamon)

// Add the Cinnamon Agent for run and test
cinnamon in run := true
cinnamon in test := true

resolvers in ThisBuild += "lightbend-commercial-mvn" at
    "https://repo.lightbend.com/pass/z5_Hk6iM1SAJgzjvgxlw9r4JmTOs7ub0W0yDEVpav8UFiXc1/commercial-releases"
resolvers in ThisBuild += Resolver.url("lightbend-commercial-ivy",
  url("https://repo.lightbend.com/pass/z5_Hk6iM1SAJgzjvgxlw9r4JmTOs7ub0W0yDEVpav8UFiXc1/commercial-releases"))(Resolver.ivyStylePatterns)

name := "akka-quickstart-java"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  // Use Coda Hale Metrics and Akka instrumentation
  Cinnamon.library.cinnamonCHMetrics,
  Cinnamon.library.cinnamonAkkaTyped,
  Cinnamon.library.cinnamonAkka,
  Cinnamon.library.cinnamonAkkaTyped,
  Cinnamon.library.cinnamonAkkaPersistence,
  Cinnamon.library.cinnamonAkkaStream,

  // Use Akka HTTP instrumentation
  Cinnamon.library.cinnamonPrometheusHttpServer,
  Cinnamon.library.cinnamonPrometheus,

  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "junit" % "junit" % "4.12" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test
)
