package me.archdev.restapi.services

import me.archdev.restapi.models.db.MovieTable
import me.archdev.restapi.models.Movie

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MoviesService extends MoviesService

trait MoviesService extends MovieTable {

  import driver.api._

  def getMovies(): Future[Seq[Movie]] = db.run(movies.result)

  def getMovieById(id: String): Future[Option[Movie]] = db.run(movies.filter(_.imdbid === id).result.headOption)

  def createMovie(movie: Movie): Future[Movie] = db.run(movies returning movies += movie)

}