package me.archdev.restapi.models.db


import me.archdev.restapi.models.Movie
import me.archdev.restapi.utils.DatabaseConfig

trait MovieTable extends DatabaseConfig {

  import driver.api._

  class Movies(tag: Tag) extends Table[Movie](tag, "movies") {
    def imdbid = column[String]("imdbid", O.PrimaryKey)
    def title = column[String]("title")

    def * = (imdbid, title) <> ((Movie.apply _).tupled, Movie.unapply)
  }

  protected val movies = TableQuery[Movies]

}