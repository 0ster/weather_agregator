error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/controllers/WeatherController.scala:aggregateWeatherData.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/controllers/WeatherController.scala
empty definition using pc, found symbol in pc: aggregateWeatherData.
semanticdb not found
empty definition using fallback
non-local guesses:
	 -javax/inject/weatherService/aggregateWeatherData.
	 -javax/inject/weatherService/aggregateWeatherData#
	 -javax/inject/weatherService/aggregateWeatherData().
	 -play/api/weatherService/aggregateWeatherData.
	 -play/api/weatherService/aggregateWeatherData#
	 -play/api/weatherService/aggregateWeatherData().
	 -play/api/mvc/weatherService/aggregateWeatherData.
	 -play/api/mvc/weatherService/aggregateWeatherData#
	 -play/api/mvc/weatherService/aggregateWeatherData().
	 -models/weatherService/aggregateWeatherData.
	 -models/weatherService/aggregateWeatherData#
	 -models/weatherService/aggregateWeatherData().
	 -Services.weatherService.aggregateWeatherData.
	 -Services.weatherService.aggregateWeatherData#
	 -Services.weatherService.aggregateWeatherData().
	 -play/api/libs/json/weatherService/aggregateWeatherData.
	 -play/api/libs/json/weatherService/aggregateWeatherData#
	 -play/api/libs/json/weatherService/aggregateWeatherData().
	 -weatherService/aggregateWeatherData.
	 -weatherService/aggregateWeatherData#
	 -weatherService/aggregateWeatherData().
	 -scala/Predef.weatherService.aggregateWeatherData.
	 -scala/Predef.weatherService.aggregateWeatherData#
	 -scala/Predef.weatherService.aggregateWeatherData().
offset: 2575
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/controllers/WeatherController.scala
text:
```scala
package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._
import Services._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.libs.json._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class WeatherController @Inject()(val controllerComponents: ControllerComponents, weatherService: WeatherDataService[Future])(implicit ec: ExecutionContext) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getAllWeather(): Action[AnyContent] = Action.async{
    weatherService.getAll().map{data =>
      Ok(views.html.allWeather(data.toSeq))
    }
  }

  def getWeatherByLocation(location: String): Action[AnyContent] = Action.async{ 
    weatherService.getWeatherByLocation(location).map{data =>
      if (data.isEmpty) NotFound(s"No weather data found for location: $location")
      else Ok(views.html.weatherByLocation(location, data))
    }
  }

  def getWeatherByService(service: String): Action[AnyContent] = Action.async{
    weatherService.getWeatherByService(service).map{
      case Some(data) => Ok(views.html.weatherByService(service, data))
      case None => NotFound(s"No weather data found for service: $service")
    }
  }

  def insert(): Action[JsValue] = Action(parse.json).async{request =>
    request.body.validate[WeatherDataModel].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(errors))))
      },
      weatherData => {
        weatherService.insert(weatherData).map{ saved =>
          Created(Json.toJson(saved))
        }
      } 
    )
  }

  def delete(id: Int): Action[AnyContent] = Action.async{
    weatherService.delete(id).map{_=>
      NoContent
    }
  }

  def getAverageTemperature(location: String): Action[AnyContent] = Action.async{
    weatherService.getAverageTemperatureByLocation(location).map{
      case Some(avgTemp) => Ok(views.html.average(location, avgTemp))
      case None => NotFound(s"No weather data found for location: $location")
    }
  }

  def aggregateWeather(location: String): Action[AnyContent] = Action.async{
    weatherService.aggregateWeather@@Data(location).map{data =>
      Ok(Json.toJson(location, data))
    }
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: aggregateWeatherData.