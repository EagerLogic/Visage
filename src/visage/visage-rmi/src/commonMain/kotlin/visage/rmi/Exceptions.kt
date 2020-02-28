package visage.rmi

class AuthenticationFailedException() : Exception()

class AuthorizationFailedException() : Exception()

class RmiException(message: String) : Exception(message)

class RedirectException(val url: String, val hard: Boolean = false) : Exception()

class BackendException() : Exception()