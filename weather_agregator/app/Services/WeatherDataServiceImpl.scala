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
import scala.concurrent.duration._
import cats.effect.IO
import cats.effect.kernel.Concurrent


@Singleton
class WeatherDataServiceImpl @Inject()(repository: WeatherDataRepository[IO], config: Configuration, ws: WSClient)(implicit ec: ExecutionContext, materializer: Materializer) extends WeatherDataService[IO] {
    
    //Получение URL и ключей для API
    private val openMeteoUrl: String = config.get[String]("weather.services.openMeteo.url")

    private val weatherApiUrl: String = config.get[String]("weather.services.weatherApi.url")
    private val weatherApiKey: String = config.get[String]("weather.services.weatherApi.apiKey")

    private val visualcrossingUrl: String = config.get[String]("weather.services.visualcrossing.url")
    private val visualcrossingKey: String = config.get[String]("weather.services.visualcrossing.apiKey")

    private val nominatimUrl: String = config.get[String]("weather.services.nominatim.url")

    //Список сервисов
    private val services = List("OpenMeteo", "WeatherAPI", "VisualCrossing")

    //Список городов
    private val locations = List("Nizhny Novgorod", "Yekaterinburg", "Vladivostok")

    //Интервал запросов
    private val interval = 5.minutes

    //Получение погоды по локации
    override def getWeatherByLocation(location: String): IO[Seq[WeatherModel]] = {
        repository.getWeatherByLocation(location)
    }

    //Получение всех данных о погоде
    override def getAllWeather(): IO[Seq[WeatherModel]] = {
        repository.getAllWeather()
    }
    
    // Получение последних данных о погоде для предопределенных городов и сервисов
    override def getLatestWeather(): IO[Seq[WeatherModel]] = {
        repository.getLatestWeather(locations, services)
    }

    //Вставка в репозиторий
    override def insert(weather: WeatherModel): IO[WeatherModel] = {
        repository.insert(weather)
    }
    
    //Удаление из репозитория
    override def delete(id: Int): IO[Unit] = {
        repository.delete(id)
    }

    //Получение данных от конкретного сервиса 
    def fetchWeather(serviceName: String, location: String): IO[WeatherModel] = {
        serviceName match{
            case "OpenMeteo" => fetchFromOpenMeteo(location)
            case "WeatherAPI" => fetchFromWeatherAPI(location)
            case "VisualCrossing" => fetchFromVisualCrossing(location)
            case _ => IO.raiseError(new IllegalArgumentException("Неподдерживаемый сервис погоды"))
        }
    }
    
    //Преобразование местоположения в координаты через Nominatim API для Open-Meteo
    def getCoordinates(location: String): IO[(Double, Double)] = {
        
        val encodedLocation = java.net.URLEncoder.encode(location, "UTF-8")
        val url = s"$nominatimUrl?q=$encodedLocation&format=json&limit=1"
        
        IO.fromFuture(
            IO.delay {
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
    def fetchFromOpenMeteo(location: String): IO[WeatherModel] = {
        //Геокодинг для получения координат
        getCoordinates(location).flatMap { case (latitude, longitude) =>
            val url = s"$openMeteoUrl?latitude=$latitude&longitude=$longitude&current=temperature_2m,weather_code&timezone=auto"
            IO.fromFuture(
                IO.delay {
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
    def fetchFromWeatherAPI(location: String): IO[WeatherModel] = {
        val url = s"$weatherApiUrl?key=$weatherApiKey&q=$location&aqi=no"
        IO.fromFuture(
            IO.delay {
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
    def fetchFromVisualCrossing(location: String): IO[WeatherModel] = {
        val url = s"$visualcrossingUrl?location=$location&key=$visualcrossingKey&unitGroup=metric"
        IO.fromFuture(
            IO.delay {
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

    //Сбор данных с нескольких сервисов по городам
    def fetchFromAllServices(): IO[Unit] = {
        locations.traverse_ {location =>
            services.traverse_ {service =>
                fetchWeather(service, location)
                    .flatMap(model => insert(model))
                    .handleErrorWith{e =>
                        IO.delay(println(s"Ошибка получения данных из $service для $location: ${e.getMessage}"))
                    }
            }
        }
    }

    //Непрерывный процесс сбора данных 
    override def dataCollection(): IO[Unit] = {
    
        val process: LazyList[IO[Unit]] = LazyList.continually{
            for{
                _ <- IO.delay(println(s"Новый цикл запросов: ${LocalDateTime.now()}"))
                _ <- fetchFromAllServices()
                _ <- IO.sleep(interval)
            } yield()
        }

        def loop(stream: LazyList[IO[Unit]]): IO[Unit] = { 
            stream match {
                case head #:: tail => head.flatMap(_ => loop(tail))
                case _ => IO.unit 
            }
        }

        //Запуск процесса
        Concurrent[IO].start(loop(process)).void
    }
}

