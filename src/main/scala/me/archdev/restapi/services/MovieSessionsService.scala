package me.archdev.restapi.services

import me.archdev.restapi.models.db.MovieSessionTable
import me.archdev.restapi.models.{ MovieSession, MovieSessionRequest }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import me.archdev.restapi.models.ReserveSeat

object MovieSessionsService extends MovieSessionsService

trait MovieSessionsService extends MovieSessionTable {

  import driver.api._

  def getSessions(movieId: String): Future[Seq[MovieSession]] = db.run(movieSessions.filter(_.imdbid === movieId).result)

  def getMovieSessionById(screenId: String): Future[Option[MovieSession]] = db.run(movieSessions.filter(_.screenId === screenId).result.headOption)

  def createMovieSession(movie: MovieSession): Future[MovieSession] = 
    db.run(movieSessions returning movieSessions += movie)
  
  def reserveSeat(reserve: ReserveSeat): Future[Option[MovieSession]] = getMovieSessionById(reserve.screenId).flatMap {
    case Some(session) =>
      if(session.thereIsAvailableSeat) {
        val sessionUpdated = session.copy(reservedSeats = session.reservedSeats + 1)
        db.run(movieSessions.filter(_.screenId === reserve.screenId).update(sessionUpdated)).map(_ => Some(sessionUpdated))  
      } else {
        Future.successful(None)
      }
    case None => Future.successful(None)
  }
}