package visage.rmi

interface IVisageResponse {
    val nativeResponse: Any
    fun sendResponse(status: Int, body: String)
    fun addHeader(name: String, value: String)
    fun addCookie(name: String, value: String, maxAge: Int, path: String = "/")
    fun removeCookie(name: String)
}
