package visage.rmi

interface IVisageRequest {

    val headers: Map<String, List<String>>
    val cookies: Map<String, Cookie>
    val nativeRequest: Any

    fun readContent(): String
}
