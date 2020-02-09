package visage.core

class MRouter internal constructor() : APureComponent() {
    var isFirstMatchingChildFound = false

    override fun Components.render(children: List<AComponent<*>>) {
        for (child in children) {
            if (child is MRoute) {
                if (child.isMatchingPath) {
                    this.addChild(child)
                    break
                }
            }
        }
    }

}

fun Components.Router(init: MRouter.() -> Unit) = this.registerComponent(MRouter(), init)

class MRoute internal constructor(
    private val pathMatcher: (currentPath: LocationItem) -> Boolean,
    private val pathTemplate: String? = null
) : APureComposite() {

    constructor(path: String) : this({ pathMatcherFun(it, path) }, path)

    var params: Map<String, String> = mutableMapOf()
    var isMatchingPath = false

    init {
        isMatchingPath = this.pathMatcher(Navigation.currentLocation)

        if (isMatchingPath && pathTemplate != null) {
            this.params = Navigation.currentLocation.path.extract(pathTemplate)
        }
    }

    override fun Components.render(children: List<AComponent<*>>) {
        children.forEach {
            this.addChild(it)
        }
    }


    companion object {
        fun pathMatcherFun(currentPath: LocationItem, path: String): Boolean {
            val pathWithoutParams = path.replace("\\{([^\\{\\}]+)\\}".toRegex(), "(.+)").replace("*", "(.+)")

            return Regex(pathWithoutParams).matches(currentPath.path)
        }
    }

}

fun MRouter.route(path: String, init: MRoute.() -> Unit) = this.registerComponent(MRoute(path)) {
    if (!this@route.isFirstMatchingChildFound && this.isMatchingPath) {
        this@route.isFirstMatchingChildFound = true
        init()
    }
}
fun MRouter.route(pathMatcher: (currentPath: LocationItem) -> Boolean, init: MRoute.() -> Unit) = this.registerComponent(MRoute(pathMatcher)) {
    if (!this@route.isFirstMatchingChildFound && this.isMatchingPath) {
        this@route.isFirstMatchingChildFound = true
        init()
    }
}

fun String.extract(template: String, delimiters: List<String> = listOf("\\{", "\\}")): Map<String, String> {
    val specialCharRegex = """[\\/\\\\\^\\+\\.\\?\\(\\)]""".toRegex()
    val tokenRegex = "${delimiters[0]}([^${delimiters.joinToString("")}]+)${delimiters[1]}".toRegex()
    val tokens = tokenRegex.findAll(template).map {it.value}.toList()
    val variables = tokens.map { it.replace("[${delimiters.joinToString("")}]".toRegex(), "") }

    var templateString = template.replace(specialCharRegex) { "\\${it.value}" }
    tokens.forEach {
        templateString = templateString.replace(it, "(.+)")
    }
    val templateRegex = templateString.toRegex()

    val findResult = templateRegex.find(this)
    var firstSkipped = false
    val values = mutableListOf<String>()
    findResult?.groups?.forEach {
        if (!firstSkipped) {
            firstSkipped = true
        } else {
            values.add(it!!.value)
        }
    }

    return variables.zip(values).toMap()
}
