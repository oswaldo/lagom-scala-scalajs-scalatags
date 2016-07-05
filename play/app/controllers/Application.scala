package controllers

import play.api._
import play.api.http._
import play.api.mvc._
import scalatags._
import scalatags.Text._
import scalatags.Text.all._
import shared.SharedMessages

object Application extends Controller {
  def index = Action {
    Ok(indexView(SharedMessages.itWorks).toString)
      .withHeaders(CONTENT_TYPE -> MimeTypes.HTML)
  }

  def indexView(message: String) = {
    val body = Seq(h2("Scalatags page generated on the server side"),
                   ul(li("Play shouts out: ", em(message))))

    //not really equivalent to the example in https://github.com/vmunier/play-with-scalajs-example/blob/master/client/src/main/scala/example/ScalaJSExample.scala
    //as the dom is already at hand

    mainView(body)
  }

  def mainView(content: Seq[Modifier]) = {
    html(
        body(content,
             // include the Scala.js scripts that sbt-play-scalajs has copied from the "client" project to the Play public target folder
             scripts("client")))
  }

  def scripts(projectName: String) =
    Seq(selectScript(projectName), launcher(projectName))

  def selectScript(projectName: String): TypedTag[String] = {
    if (Play.isProd(Play.current)) {
      script(src := s"/assets/${projectName.toLowerCase}-opt.js",
             `type` := "text/javascript")
    } else {
      script(src := s"/assets/${projectName.toLowerCase}-fastopt.js",
             `type` := "text/javascript")
    }
  }

  def launcher(projectName: String) = {
    script(src := s"/assets/${projectName.toLowerCase}-launcher.js",
           `type` := "text/javascript")
  }

}
