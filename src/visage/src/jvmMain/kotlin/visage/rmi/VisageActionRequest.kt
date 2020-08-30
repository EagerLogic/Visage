package visage.rmi

class VisageActionRequest(private val request: IVisageRequest) {

    val nativeRequest: Any
        get() {
            return request.nativeRequest
        }

    val headers: Map<String, List<String>>
        get() {
            return request.headers
        }

    val cookies: Map<String, Cookie>
        get() {
            return request.cookies
        }

}
