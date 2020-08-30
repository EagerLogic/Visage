package visage.core

class Portal {

    private val renderers = mutableListOf<Components.() -> Unit>()

    fun render(init: Components.() -> Unit) {
        this.renderers.add(init)
    }

    fun doRender(root: Components) {
        renderers.forEach {
            console.log("rendering modal")
            root.it()
        }
        renderers.clear()
    }

}