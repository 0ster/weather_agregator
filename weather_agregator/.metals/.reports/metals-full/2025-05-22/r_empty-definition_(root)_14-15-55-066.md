error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Modules/Module.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Modules/Module.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:
	 -Services.FlywayMigrator.
	 -Repositories.FlywayMigrator.
	 -FlywayMigrator.
	 -scala/Predef.FlywayMigrator.
offset: 837
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/Modules/Module.scala
text:
```scala
package Modules

import Services._
import Repositories._
import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import scala.concurrent.Future
import akka.stream.Materializer
import akka.actor.ActorSystem
import javax.inject.{Inject, Provider, Singleton}
import play.api.inject.ApplicationLifecycle

@Singleton
class MaterializerProviderImpl @Inject()(lifecycle: ApplicationLifecycle) extends Provider[Materializer] {
    private val actorSystem = ActorSystem("weather-app")
    
    // Регистрируем shutdown hook
    lifecycle.addStopHook(() => {
        scala.concurrent.Future.successful(actorSystem.terminate())
    })
    
    override def get(): Materializer = Materializer(actorSystem)
}

class Module extends AbstractModule {
    override def configure(): Unit = {
        //Миграция 
        FlywayMigrator@@.migrate()
        
        // Репозиторий
        bind(new TypeLiteral[WeatherDataRepository[Future]](){}).to(classOf[WeatherDataRepositoryImpl])

        // Сервис
        bind(new TypeLiteral[WeatherDataService[Future]](){}).to(classOf[WeatherDataServiceImpl])
        
        // Materializer для WSClient
        bind(classOf[Materializer]).toProvider(classOf[MaterializerProviderImpl]).asEagerSingleton()
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.