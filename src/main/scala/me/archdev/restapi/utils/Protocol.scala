package me.archdev.restapi.utils

import me.archdev.restapi.models._
import spray.json.DefaultJsonProtocol

trait Protocol extends DefaultJsonProtocol {
  implicit val usersFormat = jsonFormat3(UserEntity)
  implicit val tokenFormat = jsonFormat3(TokenEntity)
  implicit val movieSessionFormat = jsonFormat3(MovieSession)
  implicit val movieFormat = jsonFormat2(Movie)
}
