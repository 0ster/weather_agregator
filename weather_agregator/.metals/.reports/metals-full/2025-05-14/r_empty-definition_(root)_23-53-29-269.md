error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherDataRepositoryImpl.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherDataRepositoryImpl.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 734
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherDataRepositoryImpl.scala
text:
```scala
package Repositories

import models.WeatherDataModel
import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.db.Database

@Singleton
class WeatherDataRepositoryImpl @Inject()(db: Database)(implicit ec: ExecutionContext) extends WeatherDataRepository{
    override def getAll():Future[Seq[WeatherDataModel]] = Future{
        db.withConnection{conn =>
            val stmt = conn.prepareStatement("SELECT * FROM weather")
            val rs = stmt.executeQuery()
            val buffer = scala.collection.mutable.Buffer[WeatherDataModel]()
            while(rs.next()){
                rs.getInt("id"),
                rs.getInt("serviceId"),
                rs.getString("location@@"),
                rs.getDecimal("temperature"),
                rs.getString("metcast"),
                rs.getTimestamp("dataAndTime").toLocalDateTime
            }
        }
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.