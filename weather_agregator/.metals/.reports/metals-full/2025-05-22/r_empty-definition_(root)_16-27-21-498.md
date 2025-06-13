error id: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/utils/FlywayMigrator.scala:`<none>`.
file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/utils/FlywayMigrator.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 204
uri: file:///D:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/app/utils/FlywayMigrator.scala
text:
```scala
package utils

import org.flywaydb.core.Flyway

object FlywayMigrator {
  def migrate(): Unit = {
    val flyway = Flyway.configure()
      .dataSource(
        "jdbc:postgresql://localhost:5432/weatherdb@@",
        "postgres",
        "password"
      )
      .locations("classpath:/db/migration")
      .schemas("public")
      .baselineOnMigrate(true)
      .load()

    flyway.migrate()
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.