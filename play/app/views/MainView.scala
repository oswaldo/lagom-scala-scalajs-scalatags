package views

import scalatags.Text._

object MainView {
  import scalatags.Text.all._

  def apply(content: Seq[Modifier])(implicit env: play.Environment) = {
    html(
        body(content,
             // include the Scala.js scripts that sbt-play-scalajs has copied from the "client" 
             // project to the Play public target folder
             scripts("scalajsclient")))
  }

  def scripts(projectName: String)(implicit env: play.Environment) =
    Seq(selectScript(projectName), launcher(projectName))

  def selectScript(projectName: String)(
      implicit env: play.Environment): TypedTag[String] = {
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
