import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import org.scalajs.dom
import scalatags.JsDom.all._
import shared.SharedMessages

object Hellp extends js.JSApp {
  def main(): Unit = {
    if (!js.isUndefined(global.window.console)) {
      global.console.log("Welcome to your Play application's JavaScript!");
    }

    dom.document
      .getElementById("scalajsclientDiv")
      .appendChild(
          ul(li("ScalaJS shouts out: ", em(SharedMessages.itWorks))).render)

  }
}
