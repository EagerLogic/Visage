package visage.rmi

import kotlin.reflect.KClass

annotation class VisageController(val props: KClass<*>, val model: KClass<*>)

annotation class VisageRemoteAction

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Authenticated(val minLevel: Int)