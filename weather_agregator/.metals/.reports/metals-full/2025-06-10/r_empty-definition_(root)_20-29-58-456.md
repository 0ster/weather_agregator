error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherDataRepository.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherDataRepository.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 317
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Repositories/WeatherDataRepository.scala
text:
```scala
package Repositories

import models.WeatherModel

//Интерфейс репозитория для работы в БД
trait WeatherDataRepository[F[_]]{
    def getWeatherByLocation(location: String):F[Seq[WeatherModel]]     //Получение данных о погоде по локации
    def getAllWeather():F[Seq[WeatherModel]]                           //Получени@@е всех данных о погоде
    def getLatestWeatherData():F[Seq[WeatherModel]]
    def insert(weather: WeatherModel):F[WeatherModel]                   //Вставка в БД
    def delete(id: Int): F[Unit]                                        //Удаление
    def getLatestWeatherData(): F[Seq[WeatherModel]]                    //Получение последних данных о погоде
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.