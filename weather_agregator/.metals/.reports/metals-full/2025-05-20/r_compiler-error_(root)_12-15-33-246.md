file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/WeatherDataServiceImpl.scala
### java.nio.file.InvalidPathException: Illegal char <:> at index 3: jar:file:///C:/Users/USER/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.16/scala-library-2.13.16-sources.jar!/scala/Int.scala

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 2213
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Services/WeatherDataServiceImpl.scala
text:
```scala
package Services

import javax.inject._
import models.WeatherDataModel
import Repositories.WeatherDataRepositoryImpl
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.libs.ws._
import play.api.libs.json._
import java.time.LocalDateTime


@Singleton
class WeatherDataServiceImpl @Inject()(repository: WeatherDataRepositoryImpl, ws: WSClient)(implicit ec: ExecutionContext) extends WeatherDataService[Future]{
    private val openWeatherKey = "openWeatherKey"
    private val weatherApiKey = "weatherApiKey"

    private val openWeatherUrl = "https://api.openweathermap.org/data/2.5/weather"
    private val weatherApiUrl = "http://api.weatherapi.com/v1/current.json"

    override def getAll(): Future[Seq[WeatherDataModel]] = {
        repository.getAll()
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
            case "OpenWeatherMap" => fetchFromOpenWeatherMap(location)
            case "WeatherAPI" => fetchFromWeatherAPI(location)
            case _ => Future.failed(new IllegalArgumentException("Unsupported weather service"))
        }
    }

    def fetchFromOpenWeatherMap(location: String): Future[WeatherDataModel] = {
        val url = s"$openWeatherUrl?q=$location&units=metric&appid=$openWeatherKey"
        ws.url(url).get().map{response =>
            response.status m@@
            val json = response.json
            val temp = (json\"main"\"temp").as[Double]
            val weather = (json \ "weather")(0).\("main").as[String]
            WeatherDataModel(
                id = 0,
                location = location,
                serviceName = "OpenWeatherMap",
                temperature = temp,
                metcast = weather,
                dateAndTime = LocalDateTime.now()
            )
        }
    }

    def fetchFromWeatherAPI(location: String): Future[WeatherDataModel] = {
        val url = s"$weatherApiUrl?q=$location&units=metric&appid=$weatherApiKey"
        ws.url(url).get().map{response => 
            val json = response.json
            val temp = (json\"main"\"temp").as[Double]
            val weather = (json\"weather")(0).\("main").as[String]
            WeatherDataModel(
                id = 0,
                location = location,
                serviceName = "WeatherAPI",
                temperature = temp,
                metcast = weather,
                dateAndTime = LocalDateTime.now()
            )
        }
    }

    def aggregateWeatherData(location: String): Future[Seq[WeatherDataModel]] = {
        val services = LazyList("OpenWeatherMap", "WeatherAPI")

        Future.sequence(services.map(fetchWeather(_, location)))
        .flatMap { weatherDataList =>
            Future.sequence(weatherDataList.map(insert)).map(_ => weatherDataList)
        }
    }
}

```



#### Error stacktrace:

```
java.base/sun.nio.fs.WindowsPathParser.normalize(WindowsPathParser.java:182)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:153)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:77)
	java.base/sun.nio.fs.WindowsPath.parse(WindowsPath.java:92)
	java.base/sun.nio.fs.WindowsFileSystem.getPath(WindowsFileSystem.java:232)
	java.base/java.nio.file.Path.of(Path.java:147)
	java.base/java.nio.file.Paths.get(Paths.java:69)
	scala.meta.io.AbsolutePath$.apply(AbsolutePath.scala:58)
	scala.meta.internal.metals.MetalsSymbolSearch.$anonfun$definitionSourceToplevels$2(MetalsSymbolSearch.scala:70)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.metals.MetalsSymbolSearch.definitionSourceToplevels(MetalsSymbolSearch.scala:69)
	dotty.tools.pc.completions.CaseKeywordCompletion$.dotty$tools$pc$completions$CaseKeywordCompletion$$$sortSubclasses(MatchCaseCompletions.scala:342)
	dotty.tools.pc.completions.CaseKeywordCompletion$.matchContribute(MatchCaseCompletions.scala:292)
	dotty.tools.pc.completions.Completions.advancedCompletions(Completions.scala:351)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:122)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:139)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:150)
```
#### Short summary: 

java.nio.file.InvalidPathException: Illegal char <:> at index 3: jar:file:///C:/Users/USER/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.16/scala-library-2.13.16-sources.jar!/scala/Int.scala