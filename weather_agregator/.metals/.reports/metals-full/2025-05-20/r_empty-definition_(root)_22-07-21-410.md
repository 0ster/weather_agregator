error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/controllers/WeatherController.scala:getWeatherByLocation.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/controllers/WeatherController.scala
empty definition using pc, found symbol in pc: getWeatherByLocation.
semanticdb not found
empty definition using fallback
non-local guesses:
	 -javax/inject/weatherService/getWeatherByLocation.
	 -javax/inject/weatherService/getWeatherByLocation#
	 -javax/inject/weatherService/getWeatherByLocation().
	 -play/api/weatherService/getWeatherByLocation.
	 -play/api/weatherService/getWeatherByLocation#
	 -play/api/weatherService/getWeatherByLocation().
	 -play/api/mvc/weatherService/getWeatherByLocation.
	 -play/api/mvc/weatherService/getWeatherByLocation#
	 -play/api/mvc/weatherService/getWeatherByLocation().
	 -models/weatherService/getWeatherByLocation.
	 -models/weatherService/getWeatherByLocation#
	 -models/weatherService/getWeatherByLocation().
	 -Services.weatherService.getWeatherByLocation.
	 -Services.weatherService.getWeatherByLocation#
	 -Services.weatherService.getWeatherByLocation().
	 -play/api/libs/json/weatherService/getWeatherByLocation.
	 -play/api/libs/json/weatherService/getWeatherByLocation#
	 -play/api/libs/json/weatherService/getWeatherByLocation().
	 -weatherService/getWeatherByLocation.
	 -weatherService/getWeatherByLocation#
	 -weatherService/getWeatherByLocation().
	 -scala/Predef.weatherService.getWeatherByLocation.
	 -scala/Predef.weatherService.getWeatherByLocation#
	 -scala/Predef.weatherService.getWeatherByLocation().
offset: 1133
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
  // def index() = Action { implicit request: Request[AnyContent] =>
  //   Ok(views.html.index())
  // }

  def getAllWeather(): Action[AnyContent] = Action.async{
    weatherService.getAll().map{data =>
      Ok(views.html.allWeather(data))
    }
  }

  def getWeatherByLocation(location: String): Action[AnyContent] = Action.async{ 
    weatherService.get@@WeatherByLocation(location).map{data =>
      if (data.isEmpty) NotFound(s"No weather data found for location: $location")
      else Ok(views.html.weatherByLocation(data))
    }
  }

  def getWeatherByService(service: String): Action[AnyContent] = Action.async{
    weatherService.getWeatherByService(service).map{
      case Some(data) => Ok(views.html.weatherByService(data))
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
    weatherService.aggregateWeatherData(location).map{data =>
      Ok(views.html.aggregate(data))
    }
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: getWeatherByLocation.