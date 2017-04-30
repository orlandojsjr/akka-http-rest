package me.archdev.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import akka.http.scaladsl.server.PathMatchers.Segment
import me.archdev.restapi.models._
import me.archdev.restapi.services.MovieSessionsService

import spray.json._

trait MovieSessionsServiceRoute extends MovieSessionsService with BaseServiceRoute {

  import StatusCodes._

  val movieSessionsRoute = pathPrefix("movies" / "[a-zA-Z0-9]*".r / "sessions") { imdbid =>
    pathEndOrSingleSlash {
      get {
        complete(getSessions(imdbid).map(_.toJson))
      } ~
        post {
           entity(as[MovieSessionRequest]) { session =>
             complete(createMovieSession(MovieSession(None, session.screenId, session.imdbid, session.availableSeats)).map(_.toJson))
           }
        } 
    } ~ 
    pathPrefix("[_a-zA-Z0-9]*".r) { screenId =>
      get {
          complete(getMovieSessionsBy(screenId, imdbid))
      } ~
        pathPrefix("reserve") { 
          pathEndOrSingleSlash { 
            post {
              entity(as[Reserve]) { reserve =>
                complete(reserveSeat(reserve))
              }
            }
          }
        }
    }
  } 
}
