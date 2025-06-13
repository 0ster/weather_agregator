package Modules

import Services._
import Repositories._
import com.google.inject.{AbstractModule, TypeLiteral}
import scala.concurrent.Future
import akka.stream.Materializer
import akka.actor.ActorSystem
import javax.inject.{Inject, Provider, Singleton}
import play.api.inject.ApplicationLifecycle
import utils.FlywayMigrator
import cats.effect.IO


@Singleton
class MaterializerProviderImpl @Inject()(lifecycle: ApplicationLifecycle) extends Provider[Materializer] {
    private val actorSystem = ActorSystem("weather")

    lifecycle.addStopHook(() => {
        scala.concurrent.Future.successful(actorSystem.terminate())
    })
    
    override def get(): Materializer = Materializer(actorSystem)
}

class Module extends AbstractModule {
    override def configure(): Unit = {
        //Миграция 
        //FlywayMigrator.migrate()

        //Привязка репозитория
        bind(new TypeLiteral[WeatherDataRepository[IO]](){}).to(classOf[WeatherDataRepositoryImpl])

        //Привязка сервиса
        bind(new TypeLiteral[WeatherDataService[IO]](){}).to(classOf[WeatherDataServiceImpl])

        //Адаптер для контроллера(Future)
        bind(new TypeLiteral[WeatherDataService[Future]](){}).to(classOf[IOToFutureAdapter])
        
        //Materializer для WSClient
        bind(classOf[Materializer]).toProvider(classOf[MaterializerProviderImpl]).asEagerSingleton()
    }
}
