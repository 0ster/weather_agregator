error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/IOToFutureAdapter.scala:unsafeToFuture.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/IOToFutureAdapter.scala
empty definition using pc, found symbol in pc: unsafeToFuture.
semanticdb not found

found definition using fallback; symbol unsafeToFuture
offset: 1114
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/IOToFutureAdapter.scala
text:
```scala
package Services

import javax.inject._
import models.WeatherModel
import cats.effect.IO
import scala.concurrent.Future
import cats.effect.unsafe.implicits.global
import scala.concurrent.ExecutionContext
import com.fasterxml.jackson.module.scala.deser.overrides


//Адаптер для преобразования WeatherDataService[IO] в WeatherDataService[Future]
@Singleton
class IOToFutureAdapter @Inject()(ioService: WeatherDataService[IO])(implicit ec: ExecutionContext) extends WeatherDataService[Future] {
  
  override def getWeatherByLocation(location: String): Future[Seq[WeatherModel]] = 
    ioService.getWeatherByLocation(location).unsafeToFuture()
  
  override def getAllWeather(): Future[Seq[WeatherModel]] = 
    ioService.getAllWeather().unsafeToFuture()
  
  override def insert(weather: WeatherModel): Future[WeatherModel] =
    ioService.insert(weather).unsafeToFuture()
  
  override def delete(id: Int): Future[Unit] =
    ioService.delete(id).unsafeToFuture()
  
  override def startPeriodicDataCollection(): Future[Unit] =
    ioService.startPeriodicDataCollection().unsafeToFuture@@()
  
  override def fetchFromAllServices(location: String): Future[Seq[WeatherModel]] =
    ioService.fetchFromAllServices(location).unsafeToFuture()
} 
```


#### Short summary: 

empty definition using pc, found symbol in pc: unsafeToFuture.