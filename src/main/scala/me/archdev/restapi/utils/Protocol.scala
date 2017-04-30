package me.archdev.restapi.utils

import me.archdev.restapi.models._
import spray.json.DefaultJsonProtocol

trait Protocol extends DefaultJsonProtocol {
  implicit val usersFormat = jsonFormat3(UserEntity)
  implicit val tokenFormat = jsonFormat3(TokenEntity)
  implicit val movieSessionFormat = jsonFormat5(MovieSession)
  implicit val movieSessionRequestFormat = jsonFormat3(MovieSessionRequest)
  implicit val movieSessionResponseFormat = jsonFormat5(MovieSessionResponse)
  implicit val movieFormat = jsonFormat2(Movie)
  implicit val reserveSeatFormat = jsonFormat2(Reserve)
}


