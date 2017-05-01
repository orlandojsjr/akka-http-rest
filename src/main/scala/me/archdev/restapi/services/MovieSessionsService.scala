package me.archdev.restapi.services

import me.archdev.restapi.models.db.MovieSessionTable
import me.archdev.restapi.models.{ MovieSession, MovieSessionRequest, MovieSessionResponse }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import me.archdev.restapi.models.Reserve

object MovieSessionsService extends MovieSessionsService

trait MovieSessionsService extends MovieSessionTable {

  import driver.api._

  def getSessions(movieId: String): Future[Seq[MovieSession]] = db.run(movieSessions.filter(_.imdbid === movieId).result)

  def getMovieSessionsBy(reserve: Reserve): Future[Option[MovieSession]] = db.run(movieSessions.filter(m => m.screenId === reserve.screenId && m.imdbid === reserve.imdbid).result.headOption)

  def createMovieSession(movie: MovieSession): Future[MovieSession] =
    db.run(movieSessions returning movieSessions += movie)

  def reserveSeat(reserve: Reserve): Future[Option[MovieSessionResponse]] = getMovieSessionsBy(reserve).flatMap {
    case Some(session) =>
      if(session.isThereAvailableSeat) {
        val sessionUpdated = session.copy(reservedSeats = session.reservedSeats + 1)
        db.run(movieSessions.filter(s => s.screenId === reserve.screenId && s.imdbid === reserve.imdbid).update(sessionUpdated)).map(_ => Some(sessionUpdated))
        getMovieSessionsBy(reserve.screenId, reserve.imdbid)
      } else {
        Future.successful(None)
      }
    case None => Future.successful(None)
  }

  def cancelReserve(reserve: Reserve): Future[Option[MovieSessionResponse]] = getMovieSessionsBy(reserve).flatMap {
    case Some(session) =>
      if(session.isThereSeatReserved) {
        val sessionUpdated = session.copy(reservedSeats = session.reservedSeats - 1)
        db.run(movieSessions.filter(s => s.screenId === reserve.screenId && s.imdbid === reserve.imdbid).update(sessionUpdated)).map(_ => Some(sessionUpdated))
        getMovieSessionsBy(reserve.screenId, reserve.imdbid)
      } else {
        Future.successful(None)
      }
    case None => Future.successful(None)
  }

  def getMovieSessionsBy(screenId: String, imdbid: String): Future[Option[MovieSessionResponse]] = {
    val result = db.run(
        (for ((movie, sessions) <- movies join movieSessions if movie.imdbid === imdbid && sessions.screenId === screenId)
          yield (movie, sessions)).result.headOption)

    result.map ( _.map {
      case (movie, session) => MovieSessionResponse(session.screenId, movie.imdbid, movie.title, session.availableSeats, session.reservedSeats)
    })
  }
}
