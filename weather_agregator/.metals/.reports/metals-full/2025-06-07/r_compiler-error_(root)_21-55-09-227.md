file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/WeatherDataServiceImpl.scala
### java.nio.file.InvalidPathException: Illegal char <:> at index 3: jar:file:///C:/Users/USER/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.16/scala-library-2.13.16-sources.jar!/scala/Int.scala

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1394
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/WeatherDataServiceImpl.scala
text:
```scala
package Services

import javax.inject._
import models.WeatherModel
import Repositories._
import scala.concurrent.ExecutionContext
import play.api.libs.ws._
import play.api.libs.json._
import play.api.Configuration
import java.time.LocalDateTime
import akka.stream.Materializer
import cats.effect.unsafe.implicits.global
import cats.syntax.all._
import cats.effect.kernel.Async
import cats.implicits._


@Singleton
class WeatherDataServiceImpl[F[_]: Async] @Inject()(repository: WeatherDataRepository[F], config: Configuration, ws: WSClient)(implicit ec: ExecutionContext, materializer: Materializer) extends WeatherDataService[F] {

    private val F = Async[F]
    
    //Получение URL и ключей для API
    val openMeteoUrl: String = config.get[String]("weather.services.openMeteo.url")

    val weatherApiUrl: String = config.get[String]("weather.services.weatherApi.url")
    val weatherApiKey: String = config.get[String]("weather.services.weatherApi.apiKey")

    val visualcrossingUrl: String = config.get[String]("weather.services.visualcrossing.url")
    val visualcrossingKey: String = config.get[String]("weather.services.visualcrossing.apiKey")

    val nominatimUrl: String = config.get[String]("weather.services.nominatim.url")

    //Список городов
    val locations = LazyList("Moscow", "Saint Petersburg", "Nizhny Novgorod", "Novosibirsk", "Vladivostok")

    val interval = 2 m@@


    //Получение погоды по локации
    override def getWeatherByLocation(location: String): F[Seq[WeatherModel]] = {
        repository.getWeatherByLocation(location)
    }

    //Получение всех данных о погоде
    override def getAllWeather(): F[Seq[WeatherModel]] = {
        repository.getAllWeather()
    }

    //Вставка в репозиторий
    override def insert(weather: WeatherModel): F[WeatherModel] = {
        repository.insert(weather)
    }
    
    //Удаление из репозитория
    override def delete(id: Int): F[Unit] = {
        repository.delete(id)
    }

    //Получение данных от конкретного сервиса 
    def fetchWeather(serviceName: String, location: String): F[WeatherModel] = {
        serviceName match{
            case "OpenMeteo" => fetchFromOpenMeteo(location)
            case "WeatherAPI" => fetchFromWeatherAPI(location)
            case "VisualCrossing" => fetchFromVisualCrossing(location)
            case _ => F.raiseError(new IllegalArgumentException("Неподдерживаемый сервис погоды"))
        }
    }
    
    //Преобразование местоположения в координаты через Nominatim API для Open-Meteo
    def getCoordinates(location: String): F[(Double, Double)] = {
        
        val encodedLocation = java.net.URLEncoder.encode(location, "UTF-8")
        val url = s"$nominatimUrl?q=$encodedLocation&format=json&limit=1"
        
        F.fromFuture(
            F.delay {
                ws.url(url)
                  .withHttpHeaders("User-Agent" -> "WeatherAggregator")
                  .get()
                  .map { response =>
                      val json = response.json.as[JsArray]
                      if (json.value.isEmpty) {
                          throw new IllegalArgumentException(s"Местоположение не найдено: $location")
                      } 
                      else {
                          val result = json.value.head
                          val lat = (result \ "lat").as[String].toDouble    //Значение широты
                          val lon = (result \ "lon").as[String].toDouble    //Значение долготы
                          (lat, lon)
                      }
                    }
            }
        )
    }

    //Получение данных от Open-Meteo
    def fetchFromOpenMeteo(location: String): F[WeatherModel] = {
        //Геокодинг для получения координат
        getCoordinates(location).flatMap { case (latitude, longitude) =>
            val url = s"$openMeteoUrl?latitude=$latitude&longitude=$longitude&current=temperature_2m,weather_code&timezone=auto"
            F.fromFuture(
                F.delay {
                    ws.url(url).get().map { response =>
                        val json = response.json
                        val temp = (json \ "current" \ "temperature_2m").as[Double]     //Извлечение температуры
                        val weatherCode = (json \ "current" \ "weather_code").as[Int]   //Извлечение кода погоды
                        val weather = weatherCodeToDescription(weatherCode)            //Преобразование кода погоды в текстовое описание
                        
                        WeatherModel(
                            id = 0,
                            location = location,
                            serviceName = "OpenMeteo",
                            temperature = temp,
                            metcast = weather,
                            dateAndTime = LocalDateTime.now()
                        )
                    }
                }
            )
        }
    }
    
    // Функция для преобразования кодов погоды Open-Meteo в текстовые описания
    private def weatherCodeToDescription(code: Int): String = code match {
        case 0 => "Clear sky"
        case 1 | 2 | 3 => "Partly cloudy"
        case 45 | 48 => "Fog"
        case 51 | 53 | 55 => "Drizzle"
        case 56 | 57 => "Freezing Drizzle"
        case 61 | 63 | 65 => "Rain"
        case 66 | 67 => "Freezing Rain"
        case 71 | 73 | 75 => "Snow"
        case 77 => "Snow grains"
        case 80 | 81 | 82 => "Rain showers"
        case 85 | 86 => "Snow showers"
        case 95 => "Thunderstorm"
        case 96 | 99 => "Thunderstorm with hail"
        case _ => "Unknown"
    }

    //Получение данных от WeatherAPI
    def fetchFromWeatherAPI(location: String): F[WeatherModel] = {
        val url = s"$weatherApiUrl?key=$weatherApiKey&q=$location&aqi=no"
        F.fromFuture(
            F.delay {
                ws.url(url).get().map{response =>
                    val json = response.json
                    val temp = (json \ "current" \ "temp_c").as[Double]     //Извлечение температуры
                    val weather = (json \ "current" \ "condition" \ "text").as[String]  //Извлечение погоды 
                    
                    WeatherModel(
                        id = 0,
                        location = location,
                        serviceName = "WeatherAPI",
                        temperature = temp,
                        metcast = weather,
                        dateAndTime = LocalDateTime.now()
                    )
                }
            }
        )
    }

    //Получение данных от VisualCrossing
    def fetchFromVisualCrossing(location: String): F[WeatherModel] = {
        val url = s"$visualcrossingUrl?location=$location&key=$visualcrossingKey&unitGroup=metric"
        F.fromFuture(
            F.delay {
                ws.url(url).get().map{response =>
                    val json = response.json
                    val temp = (json \ "currentConditions" \ "temp").as[Double]     //Извлечение температуры 
                    val weather = (json \ "currentConditions" \ "conditions").as[String]    //Извлечение погоды

                    WeatherModel(
                        id = 0,
                        location = location,
                        serviceName = "VisualCrossing",
                        temperature = temp,
                        metcast = weather,
                        dateAndTime = LocalDateTime.now() 
                    )
                }
            }
        )
    }

    //Агрегация данных от нескольких сервисов
    override def aggregateWeatherData(location: String): F[Seq[WeatherModel]] = {
        val services = LazyList("OpenMeteo", "WeatherAPI", "VisualCrossing")

        def lazyFetchWeather(service: String): F[Option[WeatherModel]] = {
            fetchWeather(service, location)
            .map(Some(_))
            .handleErrorWith { e => 
                F.delay {
                    println(s"Не удалось получить из $service: ${e.getMessage}")
                    None
                }
            }
        }

        //foldLeft для последовательной обработки сервисов
        services.foldLeftM(List.empty[WeatherModel]) { (acc, service) =>
            lazyFetchWeather(service).flatMap {
            case Some(weather) => 
                insert(weather).map(_ => weather :: acc)
            case None => 
                F.pure(acc)
            }
        }.map(_.reverse) 
    }
}

```



#### Error stacktrace:

```
java.base/sun.nio.fs.WindowsPathParser.normalize(WindowsPathParser.java:182)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:153)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:77)
	java.base/sun.nio.fs.WindowsPath.parse(WindowsPath.java:92)
	java.base/sun.nio.fs.WindowsFileSystem.getPath(WindowsFileSystem.java:232)
	java.base/java.nio.file.Path.of(Path.java:147)
	java.base/java.nio.file.Paths.get(Paths.java:69)
	scala.meta.io.AbsolutePath$.apply(AbsolutePath.scala:58)
	scala.meta.internal.metals.MetalsSymbolSearch.$anonfun$definitionSourceToplevels$2(MetalsSymbolSearch.scala:70)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.metals.MetalsSymbolSearch.definitionSourceToplevels(MetalsSymbolSearch.scala:69)
	dotty.tools.pc.completions.CaseKeywordCompletion$.dotty$tools$pc$completions$CaseKeywordCompletion$$$sortSubclasses(MatchCaseCompletions.scala:342)
	dotty.tools.pc.completions.CaseKeywordCompletion$.matchContribute(MatchCaseCompletions.scala:292)
	dotty.tools.pc.completions.Completions.advancedCompletions(Completions.scala:351)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:122)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:139)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:150)
```
#### Short summary: 

java.nio.file.InvalidPathException: Illegal char <:> at index 3: jar:file:///C:/Users/USER/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.16/scala-library-2.13.16-sources.jar!/scala/Int.scala