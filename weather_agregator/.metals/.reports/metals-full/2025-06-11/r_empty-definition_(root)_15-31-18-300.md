error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/IOToFutureAdapter.scala:startPeriodicDataCollection.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/IOToFutureAdapter.scala
empty definition using pc, found symbol in pc: startPeriodicDataCollection.
semanticdb not found
empty definition using fallback
non-local guesses:
	 -javax/inject/ioService/startPeriodicDataCollection.
	 -javax/inject/ioService/startPeriodicDataCollection#
	 -javax/inject/ioService/startPeriodicDataCollection().
	 -ioService/startPeriodicDataCollection.
	 -ioService/startPeriodicDataCollection#
	 -ioService/startPeriodicDataCollection().
	 -scala/Predef.ioService.startPeriodicDataCollection.
	 -scala/Predef.ioService.startPeriodicDataCollection#
	 -scala/Predef.ioService.startPeriodicDataCollection().
offset: 1185
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
  
  override def getLatestWeather(): Future[Seq[WeatherModel]] =
    ioService.getLatestWeather().unsafeToFuture()
  
  override def insert(weather: WeatherModel): Future[WeatherModel] =
    ioService.insert(weather).unsafeToFuture()
  
  override def delete(id: Int): Future[Unit] =
    ioService.delete(id).unsafeToFuture()
  
  override def startPeriodicDataCollection(): Future[Unit] =
    ioService.dataCollection().unsafeToFu@@ture()
} 
```


#### Short summary: 

empty definition using pc, found symbol in pc: startPeriodicDataCollection.