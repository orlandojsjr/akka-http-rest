CREATE TABLE "movie_sessions" (
  "screen_id" VARCHAR NOT NULL PRIMARY KEY,
  "imdbid" VARCHAR NOT NULL,
  "available_seats" BIGINT NOT NULL
);
ALTER TABLE "movie_sessions" ADD CONSTRAINT "MOVIE_FK" FOREIGN KEY ("imdbid") REFERENCES "movies" ("imdbid") ON UPDATE RESTRICT ON DELETE CASCADE;