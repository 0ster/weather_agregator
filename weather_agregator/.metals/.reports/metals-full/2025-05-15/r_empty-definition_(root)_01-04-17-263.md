error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherServiceRepositoryImpl.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherServiceRepositoryImpl.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 332
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherServiceRepositoryImpl.scala
text:
```scala
package Repositories

import models.WeatherServiceModel
import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.db.Database
import java.time.LocalDateTime
import java.sql.ResultSet
import java.sql.Timestamp

@Singleton
class WeatherServiceRepositoryImpl @Inject()(db: Database)(@@implicit ec: ExecutionContext) extends WeatherServiceRepository{
    override def getAllService(): Future[Seq[WeatherServiceModel]] = Future{}

    override def getServiceById(id: Int): Future[String] = Future{}

    override def getServiceByName()
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.