package visage.rmi

object Rmi {

    var redirectHandler: ((url: String, isHard: Boolean) -> Unit)? = null
    var authenticationFailedHandler: (() -> Unit)? = null
    var authorizationFailedHandler: (() -> Unit)? = null
    var rmiExceptionHandler: ((message: String) -> Unit)? = null
    var backendErrorHandler: (() -> Unit)? = null
    var frontendErrorHandle: ((ex: Throwable) -> Unit)? = null

}