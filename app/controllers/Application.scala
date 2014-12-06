package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok("POST to either /basic or /showcase. See project readme for more information.")
  }

}