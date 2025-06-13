file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/controllers/WeatherController.scala
### java.lang.IndexOutOfBoundsException: -1

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1315
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
class HomeController @Inject()(val controllerComponents: ControllerComponents, weatherService: WeatherDataServiceImpl)(implicit ec: ExecutionContext) extends BaseController {

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
      Ok(views.html.weatherByService)
    }
  }

  def insert() = Action(@@).async{
    request.body.validate[WeatherDataModel].fold
  }

  def delete(id: Int) = Action.async{
    weatherService.delete(id).map{
      _=>NoContent
    }
  }

  def aggregateWeather(location: String) = Action.async{
    weatherService.aggregateWeatherData(location).map{data =>
      Ok(views.html.aggregate(data))
    }
  }


}

```



#### Error stacktrace:

```
scala.collection.LinearSeqOps.apply(LinearSeq.scala:129)
	scala.collection.LinearSeqOps.apply$(LinearSeq.scala:128)
	scala.collection.immutable.List.apply(List.scala:79)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:244)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:101)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:88)
	dotty.tools.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:46)
	dotty.tools.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:435)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: -1