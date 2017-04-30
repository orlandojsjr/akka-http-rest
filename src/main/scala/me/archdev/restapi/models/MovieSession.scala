package me.archdev.restapi.models

case class MovieSessionRequest(screenId: String, imdbid: String, availableSeats: Long)

case class MovieSessionResponse(screenId: String, imdbid: String, title: String, availableSeats: Long, reservedSeats: Long)

case class MovieSession(screenId: String, imdbid: String, availableSeats: Long, reservedSeats: Long = 0) {
  def thereIsAvailableSeat: Boolean = availableSeats > reservedSeats
}

case class ReserveSeat(screenId: String, imdbid: String)
