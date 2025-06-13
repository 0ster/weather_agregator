error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/controllers/WeatherController.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/controllers/WeatherController.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:
	 -javax/inject/Json.
	 -play/api/Json.
	 -play/api/mvc/Json.
	 -models/Json.
	 -Services.Json.
	 -Json.
	 -scala/Predef.Json.
offset: 1621
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

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class WeatherController @Inject()(val controllerComponents: ControllerComponents, weatherService: WeatherDataServiceImpl)(implicit ec: ExecutionContext) extends BaseController {

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

  def getAllWeather() = Action.async{
    weatherService.getAll().map{data =>
      Ok(views.html.allWeather(data))
    }
  }

  def getWeatherByLocation(location: String) = Action.async{ 
    weatherService.getWeatherByLocation(location).map{data =>
      Ok(views.html.weatherByLocation(data))
    }
  }

  def getWeatherByService(service: String) = Action.async{
    weatherService.getWeatherByService(service).map{data =>
      Ok(views.html.weatherByService(data))
    }
  }

  def insert() = Action(parse.json).async{request =>
    request.body.validate[WeatherDataModel].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(errors))))
      },
      weatherData => {
        weatherService.insert(weatherData).map{ data =>
          Created(Json@@.toJson(saved))
        }
      } 
    )
  }

  def delete(id: Int) = Action.async{
    weatherService.delete(id).map{
      _=>NoContent
    }
  }

  def getAverageTemperature(location: String) = Action.async{
    weatherService.getAverageTemperatureByLocation(location).map{data =>
      Ok(views.html.average(data))
    }
  }

  def aggregateWeather(location: String) = Action.async{
    weatherService.aggregateWeatherData(location).map{data =>
      Ok(views.html.aggregate(data))
    }
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.