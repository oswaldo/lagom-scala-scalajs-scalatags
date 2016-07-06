package controllers

import play.api._
import play.api.http._
import play.api.mvc._
import scalatags._
import scalatags.Text._
import scalatags.Text.all._
import shared.SharedMessages
import javax.inject.Inject
import views.IndexView
import views.MainView

class Application @Inject()(implicit env: play.Environment)
    extends Controller {
  def index = ok(IndexView(SharedMessages.itWorks))

  def ok(view: Seq[Text.TypedTag[String]]) = Action {
    implicit val codec = Codec.utf_8
    Ok(MainView(view).toString).withHeaders(CONTENT_TYPE -> ContentTypes.HTML)
  }

}
