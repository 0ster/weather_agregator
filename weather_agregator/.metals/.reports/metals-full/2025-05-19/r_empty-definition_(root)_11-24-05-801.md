error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherServiceRepositoryImpl.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherServiceRepositoryImpl.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 2550
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

@Singleton
class WeatherServiceRepositoryImpl @Inject()(db: Database)(implicit ec: ExecutionContext) extends WeatherServiceRepository{
    override def getAllService(): Future[Seq[WeatherServiceModel]] = Future{
        db.withConnection{conn =>
            val stmt = conn.prepareStatement("SELECT * FROM weather_service")
            val rs = stmt.executeQuery()
            val buffer = scala.collection.mutable.Buffer[WeatherServiceModel]()
            while(rs.next()){
                buffer.append(
                    WeatherServiceModel(
                        rs.getInt("id"),
                        rs.getString("serviceName"),
                        rs.getString("apiUrl"),
                        rs.getString("apiKey")
                    )
                )
            }
            buffer.toSeq
        }
    }

    override def getServiceById(id: Int): Future[Option[WeatherServiceModel]] = Future{
        db.withConnection{conn =>
            val stmt = conn.prepareStatement("SELECT * FROM weather_service WHERE id = ?")
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            if(rs.next()){
                Some(
                    WeatherServiceModel(
                        rs.getInt("id"),
                        rs.getString("serviceName"),
                        rs.getString("apiUrl"),
                        rs.getString("apiKey")
                    )
                )
            }
            else None 
        }
    }

    override def getServiceByName(name: String):Future[Option[WeatherServiceModel]] = Future{
        db.withConnection{conn =>
            val stmt = conn.prepareStatement("SELECT * FROM weather_service WHERE serviceName = ?")
            stmt.setString(1, name)
            val rs = stmt.executeQuery()
            if(rs.next()){
                Some(
                    WeatherServiceModel(
                        rs.getInt("id"),
                        rs.getString("serviceName"),
                        rs.getString("apiUrl"),
                        rs.getString("apiKey")
                    )
                )
            }
            else None
        } 
    }

    override def delete(id: Int): Future[Unit] = Future{
        db.withConnection { conn =>
            val stmt = conn.prepareStatement("DELETE @@FROM weather_service WHERE id = ?")
            stmt.setInt(1, id)
            stmt.executeUpdate()
        }   
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.