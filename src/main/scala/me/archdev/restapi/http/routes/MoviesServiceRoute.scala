package me.archdev.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import akka.http.scaladsl.server.PathMatchers.Segment
import me.archdev.restapi.models._
import me.archdev.restapi.services.MoviesService

import spray.json._

trait MoviesServiceRoute extends MoviesService with BaseServiceRoute {

  import StatusCodes._

  val moviesRoute = pathPrefix("movies") {
    pathEndOrSingleSlash {
      get {
        complete(getMovies().map(_.toJson))
      } ~
        post {
           entity(as[Movie]) { movie =>
             complete(Created -> createMovie(movie).map(_.toJson))
           }
        } 
    } ~ 
    pathPrefix(Segment) { id =>
      pathEndOrSingleSlash { 
          get {
              complete(getMovieById(id))
          } ~
            put {
              entity(as[Movie]) { movie =>
                complete(updateMovie(id, movie).map(_.toJson))
              }
            } ~
              delete {
                 onSuccess(deleteMovie(id)) { ignored =>
                    complete(NoContent)
                  }
              }
      }
    }
  } 
}
