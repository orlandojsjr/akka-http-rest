package me.archdev

import me.archdev.restapi.http.HttpService
import me.archdev.restapi.models.{ TokenEntity, UserEntity, Movie, MovieSession, MovieSessionResponse, Reserve }
import me.archdev.restapi.utils.Migration
import org.scalatest._

import akka.event.{ NoLogging, LoggingAdapter }
import akka.http.scaladsl.testkit.ScalatestRouteTest

import scala.concurrent.Await
import scala.concurrent.duration._

trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with HttpService with Migration {
  protected val log: LoggingAdapter = NoLogging

  import driver.api._

  val testUsers = Seq(
    UserEntity(Some(1), "Arhelmus", "test"),
    UserEntity(Some(2), "Arch", "test"),
    UserEntity(Some(3), "Hierarh", "test")
  )

  val testTokens = Seq(
    TokenEntity(userId = Some(1)),
    TokenEntity(userId = Some(2)),
    TokenEntity(userId = Some(3))
  )

  val testMovies = Seq(
    Movie("tt0111161", "The Shawshank Redemption")
  )

  val testMovieSessions = Seq(
    MovieSession(screenId = "screen_123456", imdbid = "tt0111161", availableSeats = 50),
    MovieSession(screenId = "screen_full", imdbid = "tt0111161", availableSeats = 100, reservedSeats = 100)
  )
  val reserve = Reserve(screenId = "screen_123456", imdbid = "tt0111161")
  val reserveFull = Reserve(screenId = "screen_full", imdbid = "tt0111161")

  reloadSchema()
  Await.result(db.run(movies ++= testMovies), 10.seconds)
  Await.result(db.run(movieSessions ++= testMovieSessions), 10.seconds)
  Await.result(db.run(users ++= testUsers), 10.seconds)
  Await.result(db.run(tokens ++= testTokens), 10.seconds)
}
