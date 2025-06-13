error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/models/WeatherDataModel.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/models/WeatherDataModel.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 423
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/models/WeatherDataModel.scala
text:
```scala
package models

import play.api.libs.json._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

case class WeatherDataModel(
    id: Int,
    serviceName: String,
    location: String,
    temperature: Double,
    metcast: String,
    dateAndTime: LocalDateTime    
)

object WeatherDataModel {
  implicit val localDateTimeReads: Reads[LocalDateTime] =
    Reads.localDateTimeReads("yyyy-MM-dd'T'HH:mm@@:ss")
  implicit val localDateTimeWrites: Writes[LocalDateTime] =
    Writes.localDateTimeWrites("yyyy-MM-dd'T'HH:mm:ss")
  implicit val format: OFormat[WeatherDataModel] = Json.format[WeatherDataModel]
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.