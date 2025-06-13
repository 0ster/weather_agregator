error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/models/WeatherModel.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/models/WeatherModel.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 467
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/models/WeatherModel.scala
text:
```scala
package models

import play.api.libs.json._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//Модель данных о погоде
case class WeatherModel(
    id: Int,               
    serviceName: String,
    location: String,
    temperature: Double,
    metcast: String,
    dateAndTime: LocalDateTime    
)

object WeatherModel {
  implicit val localDateTimeFormat: Format[LocalDateTime] = Format(
    Reads.localDateTimeReads(""dd.MM.yyyy HH:mm"),@@
    Writes.temporalWrites[LocalDateTime, DateTimeFormatter](
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    )
  )

  implicit val format: OFormat[WeatherModel] = Json.format[WeatherModel]
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.