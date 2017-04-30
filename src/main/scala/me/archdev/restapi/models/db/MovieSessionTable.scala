package me.archdev.restapi.models.db


import me.archdev.restapi.models.MovieSession
import me.archdev.restapi.utils.DatabaseConfig

trait MovieSessionTable extends DatabaseConfig {

  import driver.api._

  class MovieSessions(tag: Tag) extends Table[MovieSession](tag, "movie_sessions") {
    def sessionId = column[String]("session_id", O.PrimaryKey)
    def imdbid = column[String]("imdbid")
    def availableSeats = column[Long]("available_seats")

    def * = (sessionId, imdbid, availableSeats) <> ((MovieSession.apply _).tupled, MovieSession.unapply)
  }

  protected val movieSessions = TableQuery[MovieSessions]

}