package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import java.security.MessageDigest

import scala.util.{Failure, Success, Try}

object Showcase extends Controller {

  implicit val stringInJsonFormat: Format[StringIn] = Json.format[StringIn]
  implicit val md5OutJsonFormat: Format[MD5Out] = Json.format[MD5Out]

  def basic = Action { request =>
    Ok(request.body.asText.getOrElse("no request body").toUpperCase)
  }

  def showcase = Action { request =>
    val requestBody = request.body.asText.getOrElse("no request body")
    val jsValueTry = Try(Json.parse(requestBody))
    jsValueTry match {
      case Success(jsValue) => {
        jsValue.validate[StringIn] match {
          case s: JsSuccess[StringIn] => {
            val md5 = MessageDigest.getInstance("MD5").digest(s.get.input.getBytes("UTF-8"))
              .map("%02X".format(_)).mkString
            Ok(Json.toJson(MD5Out(md5, s.get.input)))
          }
          case e: JsError => BadRequest
        }
      }
      case Failure(_) => BadRequest
    }
  }

}

case class StringIn(input: String)
case class MD5Out(md5: String, originalString: String)