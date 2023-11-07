# news-api

A simple Spring Boot REST API framework for fetching news articles. 

API Documentation

| Endpoint                       | Params                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             | Example                                                                                                                     |
|--------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| /search                        | **_q_** Keywords or phrases to search in the article title and body. Max length is 100 characters. Surround phrases with quotes ("") for exact match. Without ("") it will do an OR search. Alternatively you can use the AND / OR / NOT keywords, and optionally group these with parenthesis. Eg: crypto AND (ethereum OR litecoin) NOT bitcoin. **_searchIn_** (Optional) The fields to restrict your q search to. Possible values are title, description, content. Multiple options can be specified by separating them with a comma, for example: title,content  **count** (Optional) is an optional param with max limit of 100, default 10. | /search?q=apple%20tv&count=10,   search?q=apple%20tv&searchIn=title&count=10,   search?q="apple tv"&searchIn=title&count=10 |
| /search/top/{count}            | **_count_** returns top n articles based on porpularity. Min count value is 1, maximum is 100                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      | /search/top/5                                                                                                               |
| /search/top/{category}/{count} | returns top n articles based on the popularity of the category passed. Possible categories business, entertainment, general, health, technology. **_count_** Min count value is 1, maximum is 100.                                                                                                                                                                                                                                                                                                                                                                                                                                                 | /search/top/business/5                                                                                                      |

Response Object

| Attribute Name | Description                                                                          |
|----------------|--------------------------------------------------------------------------------------|
| articles       | The results of the request                                                           |
| sourceId       | The identifier id for the source this article came from                              |
| sourceName     | The display name for the source this article came from                               |
| author         | The author of the article                                                            |
| title          | The headline or title of the article                                                 |
| description    | A description or snippet from the article                                            |
| url            | The direct URL to the article                                                        |
| urlToImage     | The URL to a relevant image for the article                                          |
| publishedAt    | The date and time that the article was published, in UTC (+000)                      |
| content        | The unformatted content of the article, where available. This is truncated to 200 char |

#### System Requirements

1) Install Maven
2) Install git
3) Install jdk 17

#### Clone repo
````
git clone https://github.com/khalsa/news-api.git
````
#### Download dependencies
````
mvn clean install
````
#### Start application
````
mvn spring-boot:run
````
#### Run test cases
````
mvn test
````
#### Test api's
````
curl http://localhost:8080/v1/news/search?q=samsung|json_pp
curl http://localhost:8080/v1/news/search/top/5|json_pp
curl http://localhost:8080/v1/news/search/top/business/5|json_pp
````
#### Swagger for testing api's
````
http://localhost:8080/swagger-ui/index.html
````

#### Future enhancements
1) Support more search features.
2) Improve exception handling, make error messages more verbose.
3) Move secret key used for encrypting api key to environment variables or vault.
4) Containerize the application.
5) Use distributed caching like redis.
6) Implement rate limiter.

