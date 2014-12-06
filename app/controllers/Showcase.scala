package controllers

import play.api._
import play.api.mvc._

object Showcase extends Controller {

  def basic = Action { request =>
    Ok(request.body.asText.getOrElse("no request body").toUpperCase)
  }

}