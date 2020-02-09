package visage.rmi

class VisageActionResponse(private val response: IVisageResponse) {

    val nativeResponse: Any
    get () {
        return response.nativeResponse
    }

    fun addHeader(name: String, value: String) {
        response.addHeader(name, value);
    }

    fun addCookie(name: String, value: String, maxAge: Int, path: String = "/") {
        this.response.addCookie(name, value, maxAge, path)
    }

    fun removeCookie(name: String) {
        this.response.removeCookie(name)
    }

}
