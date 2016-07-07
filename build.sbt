import sbt.Project.projectToRef

lazy val clients = Seq(scalajsclient)
lazy val scalaV = "2.11.8"

lazy val playserver = (project in file("play")).settings(
  scalaVersion := scalaV,
  scalaJSProjects := clients,
  libraryDependencies ++= Seq(
    "com.lihaoyi" %% "scalatags" % "0.5.5",
    "org.webjars" % "jquery" % "3.0.0"
  )
).enablePlugins(PlayScala, LagomPlay).
  aggregate(clients.map(projectToRef): _*).
  dependsOn(sharedJvm)

lazy val scalajsclient = (project in file("scalajs")).settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value),
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    "com.lihaoyi" %%% "scalatags" % "0.5.5"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSPlay)
.dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSPlay)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val timeApi = commonSettings("time-api")
  .settings(
    scalaVersion := scalaV,
    libraryDependencies += lagomJavadslApi
  )

lazy val timeImpl = commonSettings("time-impl")
  .enablePlugins(LagomJava)
  .settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      //lagomJavadslPersistence,
      lagomJavadslTestKit
    )
  )
.settings(lagomForkedTestSettings: _*)
.dependsOn(timeApi)

def commonSettings(id: String) = Project(id, base = file(id))
  .settings(eclipseSettings: _*)
  .settings(javacOptions in compile ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation"))
  .settings(jacksonParameterNamesJavacSettings: _*) // applying it to every project even if not strictly needed.

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project playserver", _: State)) compose (onLoad in Global).value

//See https://github.com/FasterXML/jackson-module-parameter-names
lazy val jacksonParameterNamesJavacSettings = Seq(
javacOptions in compile += "-parameters"
)

//Configuration of sbteclipse
//Needed for importing the project into Eclipse
EclipseKeys.skipParents in ThisBuild := false
lazy val eclipseSettings = Seq(
  EclipseKeys.projectFlavor := EclipseProjectFlavor.Scala,
  EclipseKeys.withBundledScalaContainers := false,
  EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
  EclipseKeys.eclipseOutput := Some(".target"),
  EclipseKeys.withSource := true,
  EclipseKeys.withJavadoc := true
)

// do not delete database files on start
lagomCassandraCleanOnStart in ThisBuild := false

fork in run := true
