package controllers

import play.api._
import play.api.http._
import play.api.mvc._
import scalatags._
import scalatags.Text._
import scalatags.Text.all._
import shared.SharedMessages
import javax.inject.Inject

class Application @Inject()(env: play.Environment) extends Controller {
  def index = Action {
    Ok(indexView(SharedMessages.itWorks).toString)
      .withHeaders(CONTENT_TYPE -> MimeTypes.HTML)
  }

  def indexView(message: String) = {
    val body = Seq(h2("Scalatags page generated on the server side"),
                   ul(li("Play shouts out: ", em(message))),
                   h2("Scalatags content from scalajsclient"),
                   div(id := "scalajsclientDiv"))

    mainView(body)
  }

  def mainView(content: Seq[Modifier]) = {
    html(head(meta(charset := "UTF-8")),
         body(content,
              // include the Scala.js scripts that sbt-play-scalajs has copied from the "client" project to the Play public target folder
              scripts("scalajsclient")))
  }

  def scripts(projectName: String) =
    Seq(selectScript(projectName), launcher(projectName))

  def selectScript(projectName: String): TypedTag[String] = {
    if (env.isProd) {
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
