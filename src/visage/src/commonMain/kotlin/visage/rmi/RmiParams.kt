package visage.rmi

import kotlinx.serialization.Serializable

@Serializable
data class RmiParams (
    val controllerName: String,
    val aControllerName: String,
    val methodName: String,
    val methodParams: String,
    val currentModel: String,
    val props: String
)

@Serializable
data class RmiParamWrapper<T>(val value: T)

@Serializable
data class RmiResult(
    val type: ERmiResultType,
    val data: String
)

@Serializable
enum class ERmiResultType {
    Success,
    AuthenticationFailed,
    AuthorizationFailed,
    Redirect,
    RmiException,
    Error
}

@Serializable
data class RmiRedirectResult(
    val url: String,
    val isHard: Boolean
)

@Serializable
data class RmiExceptionResult(
    val message: String
)

@Serializable
data class RmiErrorResult(
    val message: String
)
