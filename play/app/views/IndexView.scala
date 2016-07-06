package views

object IndexView {
  import scalatags.Text.all._

  def apply(message: String) = {
    Seq(h2("Scalatags page generated on the server side"),
        ul(li("Play shouts out: ", em(message))),
        h2("Scalatags content from scalajsclient"),
        div(id := "scalajsclientDiv"))
  }

}
