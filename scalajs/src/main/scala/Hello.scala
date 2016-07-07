import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import org.scalajs.dom
import scalatags.JsDom.all._
import shared.SharedMessages
import org.scalajs.dom.ext.Ajax
import scala.concurrent.ExecutionContext

object Hellp extends js.JSApp {

  def main(): Unit = {
    if (!js.isUndefined(global.window.console)) {
      global.console.log("Welcome to your Play application's JavaScript!");
    }
    addItems
  }

  def addItems = {
    import scala.concurrent.ExecutionContext.Implicits.global
    dom.document
      .getElementById("scalajsclientDiv")
      .appendChild(ul(id := "itemList")(li("ScalaJS shouts out: ", em(SharedMessages.itWorks))).render)

    timeAt("UTC")
    timeAt("ECT")

  }

  def timeAt(tz: String)(implicit ec: ExecutionContext) = {
    Ajax.get("/api/timeAt/" + tz).onSuccess {
      case xhr =>
        dom.document
          .getElementById("itemList").appendChild(li(s"Time service (timeAt $tz): ", em(xhr.responseText)).render)
    }
  }

}
