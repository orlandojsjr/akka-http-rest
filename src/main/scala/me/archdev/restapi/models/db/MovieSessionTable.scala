package me.archdev.restapi.models.db


import me.archdev.restapi.models.MovieSession
import me.archdev.restapi.utils.DatabaseConfig

trait MovieSessionTable extends MovieTable with DatabaseConfig {

  import driver.api._

  class MovieSessions(tag: Tag) extends Table[MovieSession](tag, "movie_sessions") {
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def screenId = column[String]("screen_id")
    def imdbid = column[String]("imdbid")
    def availableSeats = column[Long]("available_seats")
    def reservedSeats = column[Long]("reserved_seats")

    def * = (id, screenId, imdbid, availableSeats, reservedSeats) <> ((MovieSession.apply _).tupled, MovieSession.unapply)
  }

  protected val movieSessions = TableQuery[MovieSessions]

}