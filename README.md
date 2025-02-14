# FAVORITE-MOVIES-INFO API

This API uses the movie title or IMDB id of the movie and saves all the information related to the movie in the database. This can be used as a watchlist of the favorite movies. One can see all the movies already added in the database, add a movie to the database and delete a movie from the database.

## Endpoints
```
/movies
/show?id={id}
/show?title={title}
/add?id={id}
/add?title={title}
/find?title={title}&year={year}
/delete/{id}
```

*Developed using Java, SpringBoot, Hibernate, and Docker.*

# How to start the API server

**Create environment variables for the following**
```
- OMDB_API_KEY
- MYSQL_SERVER_USERNAME
- MYSQL_SERVER_PASSWORD
- DATABASE_URL
```

**Commands to run**
```
./mvnw clean
./mvnw package
java -jar target/*.jar
```

# How to run the API server using docker

**Create an environment file specifying the values for the following**
```
- OMDB_API_KEY
- MYSQL_SERVER_USERNAME
- MYSQL_SERVER_PASSWORD
- DATABASE_URL

e.g. OMDB_API_KEY="<api-key>"
```

**Commands to run**
```
./mvnw clean
./mvnw package
docker build -t <image-name:tag> .
docker run -p 8080:8080 --env-file <env-file-name> <image-name:tag>
```