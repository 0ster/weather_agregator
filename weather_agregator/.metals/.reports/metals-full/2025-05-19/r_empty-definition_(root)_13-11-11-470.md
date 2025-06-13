error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/WeatherDataServiceImpl.scala:getAll.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/WeatherDataServiceImpl.scala
empty definition using pc, found symbol in pc: getAll.
semanticdb not found
empty definition using fallback
non-local guesses:
	 -javax/inject/repository/getAll.
	 -javax/inject/repository/getAll#
	 -javax/inject/repository/getAll().
	 -Repositories.repository.getAll.
	 -Repositories.repository.getAll#
	 -Repositories.repository.getAll().
	 -repository/getAll.
	 -repository/getAll#
	 -repository/getAll().
	 -scala/Predef.repository.getAll.
	 -scala/Predef.repository.getAll#
	 -scala/Predef.repository.getAll().
offset: 510
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/WeatherDataServiceImpl.scala
text:
```scala
package Services

import javax.inject._
import models.WeatherDataModel
import Repositories.*
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class WeatherDataServiceImpl @Inject()(repository: WeatherDataRepository[Future])(implicit ec: ExecutionContext) extends WeatherDataService[Future]{
    lazy val services: List[String] = List(
        "OpenWeatherMap",
        "WeatherAPI",
    )

    override def getAll(): Future[Seq[WeatherDataModel]] = {
        repository.getAl@@l()
    }

    override def getWeatherByLocation(location: String): Future[Seq[WeatherDataModel]] = {
        repository.getWeatherByLocation(location)
    }

    override def getWeatherByService(service: String): Future[Option[WeatherDataModel]] = {
        repository.getWeatherByService(service)
    }

    override def insert(weather: WeatherDataModel): Future[WeatherDataModel] = {
        repository.insert(weather)
    }
    
    override def delete(id: Int): Future[Unit] = {
        repository.delete(id)
    }

    override def getAveregeTemperatureByLocation(location: String): Future[Option[Double]] = {
        repository.getWeatherByLocation(location).map{ weatherData =>
            if (weatherData.isEmpty) None
            else Some(weatherData.map(_.temperature).sum / weatherData.size)
        } 
    }

    def fetchWeather(serviceName: String, location: String): Future[WeatherDataModel] = {
        serviceName match{
            case ""
        }
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: getAll.