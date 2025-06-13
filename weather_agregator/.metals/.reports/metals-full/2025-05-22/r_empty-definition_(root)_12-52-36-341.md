error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Modules/Module.scala:Modules/`<import>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Modules/Module.scala
empty definition using pc, found symbol in pc: Modules/`<import>`.
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 17
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Modules/Module.scala
text:
```scala
package Modules

@@import Services._
import Repositories._
import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import scala.concurrent.Future

class Module extends AbstractModule {
    override def configure(): Unit = {
        // Репозиторий
        bind(new TypeLiteral[WeatherDataRepository[Future]](){}).to(classOf[WeatherDataRepositoryImpl])

        // Сервис
        bind(new TypeLiteral[WeatherDataService[Future]](){}).to(classOf[WeatherDataServiceImpl])
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: Modules/`<import>`.