import scala.scalajs.js
import scala.scalajs.js.Dynamic.global

object Hellp extends js.JSApp {
  def main(): Unit = {
    if (!js.isUndefined(global.window.console)) {
      global.console.log("Welcome to your Play application's JavaScript!");
    }

  }
}
