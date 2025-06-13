file:///d:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/Main.scala
### dotty.tools.dotc.MissingCoreLibraryException: Could not find package scala from compiler core libraries.
Make sure the compiler core libraries are on the classpath.
   

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 9
uri: file:///d:/Учеба/3%20курс/5%20семестр/ФП/weather_agregator/Main.scala
text:
```scala
object Ma@@

```



#### Error stacktrace:

```
dotty.tools.dotc.core.Denotations$.select$1(Denotations.scala:1314)
	dotty.tools.dotc.core.Denotations$.recurSimple$1(Denotations.scala:1342)
	dotty.tools.dotc.core.Denotations$.recur$1(Denotations.scala:1344)
	dotty.tools.dotc.core.Denotations$.staticRef(Denotations.scala:1348)
	dotty.tools.dotc.core.Symbols$.requiredPackage(Symbols.scala:901)
	dotty.tools.dotc.core.Definitions.ScalaPackageVal(Definitions.scala:213)
	dotty.tools.dotc.core.Definitions.ScalaPackageClass(Definitions.scala:216)
	dotty.tools.dotc.core.Definitions.AnyClass(Definitions.scala:276)
	dotty.tools.dotc.core.Definitions.syntheticScalaClasses(Definitions.scala:2109)
	dotty.tools.dotc.core.Definitions.syntheticCoreClasses(Definitions.scala:2123)
	dotty.tools.dotc.core.Definitions.init(Definitions.scala:2139)
	dotty.tools.dotc.core.Contexts$ContextBase.initialize(Contexts.scala:902)
	dotty.tools.dotc.core.Contexts$Context.initialize(Contexts.scala:524)
	dotty.tools.dotc.interactive.InteractiveDriver.<init>(InteractiveDriver.scala:41)
	dotty.tools.pc.CachingDriver.<init>(CachingDriver.scala:30)
	dotty.tools.pc.ScalaPresentationCompiler.$init$$$anonfun$1(ScalaPresentationCompiler.scala:85)
```
#### Short summary: 

dotty.tools.dotc.MissingCoreLibraryException: Could not find package scala from compiler core libraries.
Make sure the compiler core libraries are on the classpath.
   