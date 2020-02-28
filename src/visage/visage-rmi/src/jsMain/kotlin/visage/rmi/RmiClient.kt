package visage.rmi

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import visage.core.Event
import org.w3c.xhr.XMLHttpRequest
import visage.core.Navigation
import kotlin.browser.window
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.js.Promise

class RmiClient private constructor() {

    companion object {

        @UnstableDefault
        fun call(params: RmiParams): Promise<RmiResult> {
            val data = Json.stringify(RmiParams.serializer(), params)
            return Promise<RmiResult>() { resolve, reject ->
                val xhr = XMLHttpRequest()

                LoadIndicator.loadStarted()

                xhr.open("POST", "/visage/_rmi")
                xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8")
                xhr.onreadystatechange = {
                    if (xhr.readyState == 4.toShort()) {
                        LoadIndicator.loadEnded()

                        val resStr = xhr.responseText
                        if (xhr.status >= 200 && xhr.status <= 299) {
                            resolve(Json.parse(RmiResult.serializer(), resStr))
                        } else if (xhr.status == 500.toShort()) {
                            resolve(RmiResult(ERmiResultType.Error, ""))
                        } else  {
                            reject(Exception(resStr))
                        }
                    }
                }
                xhr.send(data)
            }
        }

        fun <T> handleResult(model: T, res: Continuation<T>, promise: Promise<RmiResult>, deserializer: DeserializationStrategy<T>) {
            promise.then() {
                when (it.type) {
                    ERmiResultType.Success -> {
                        res.resume(Json.parse(deserializer, it.data))
                    }
                    ERmiResultType.AuthenticationFailed -> {
                        if (Rmi.authenticationFailedHandler != null) {
                            Rmi.authenticationFailedHandler!!()
                            res.resume(model)
                        } else {
                            res.resumeWithException(AuthenticationFailedException())
                        }
                    }
                    ERmiResultType.AuthorizationFailed -> {
                        if (Rmi.authorizationFailedHandler != null) {
                            Rmi.authorizationFailedHandler!!()
                            res.resume(model)
                        } else {
                            res.resumeWithException(AuthorizationFailedException())
                        }
                    }
                    ERmiResultType.Redirect -> {
                        val redirectResult: RmiRedirectResult = Json.parse(RmiRedirectResult.serializer(), it.data)
                        res.resume(model)
                        if (Rmi.redirectHandler != null) {
                            Rmi.redirectHandler!!(redirectResult.url, redirectResult.isHard)
                        } else {
                            if (redirectResult.isHard) {
                                window.location.href = redirectResult.url
                            } else {
                                Navigation.pushLocation(redirectResult.url)
                            }
                        }
                    }
                    ERmiResultType.RmiException -> {
                        val message = it.data
                        if (Rmi.rmiExceptionHandler != null) {
                            Rmi.rmiExceptionHandler!!(message)
                            res.resume(model)
                        } else {
                            res.resumeWithException(RmiException(message))
                        }
                    }
                    ERmiResultType.Error -> {
                        if (Rmi.backendErrorHandler != null) {
                            Rmi.backendErrorHandler!!()
                            res.resume(model)
                        } else {
                            res.resumeWithException(BackendException())
                        }
                    }
                    else -> {
                        throw Error("Invalid result type: ${it.type.name}")
                    }
                }

            }
                .catch() {
                    if (Rmi.frontendErrorHandle != null) {
                        Rmi.frontendErrorHandle!!(it)
                        res.resume(model)
                    } else {
                        res.resumeWithException(it)
                    }
                }
        }

    }

}

/**
 * Indicates whenever a loading is in progress to an Rmi client with a Boolean flag
 */
class LoadIndicator private constructor() {
    companion object {
        var isLoading: Boolean = false
            private set

        /**
         * Global event that fires if the first loading started, or the last loading stopped
         */
        val onLoadingChanged: Event<Boolean> = Event()

        private var inProgress: Int = 0

        /**
         * Increment an internal counter that indicates how many client/server communication is in progress
         * If exactly 1 loading is in progress fires the onLoadingChanged event with "true" argument
         */
        fun loadStarted() {
            this.inProgress++

            if (this.inProgress == 1) {
                this.isLoading = true
                onLoadingChanged.fire(true)
            }
        }

        /**
         * Decrement an internal counter that indicates how many client/server communication is in progress
         * If less than 1 loading is in progress fires the onLoadingChanged event with "false" argument
         */
        fun loadEnded() {
            this.inProgress--

            if (this.inProgress < 1) {
                this.inProgress = 0
                this.isLoading = false
                onLoadingChanged.fire(false)
            }
        }
    }
}


