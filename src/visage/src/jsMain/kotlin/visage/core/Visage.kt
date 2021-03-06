package visage.core

import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLLinkElement
import org.w3c.dom.get
import visage.dom.Css
import kotlin.js.Date

internal class MigoRouteComponent(val renderMethod: Components.() -> Unit) : APureComposite() {
    override fun Components.render(children: List<AComponent<*>>) {
        this@MigoRouteComponent.renderMethod(this@MigoRouteComponent)
    }
}

class Visage private constructor() {

    companion object {

        private lateinit var rootElement: HTMLElement
        private lateinit var onRender: Components.() -> Unit
        private var tree: MigoRouteComponent? = null
        private val renderRunOnce = RunOnce {
            val start = Date.now()

            if (tree == null) {
                tree = MigoRouteComponent(this.onRender)
            }
            Reconciler.reconcileComponent(tree!!, rootElement, null)


//            val oldComponents: List<AComponent<Any>> = if (this.previousTree == null) {
//                listOf<AComponent<Any>>()
//            } else {
//                listOf(previousTree!!).unsafeCast<List<AComponent<Any>>>()
//            }
//
//            val newRoot = MigoRouteComponent(this.onRender)
//            newRoot.doRender()
//            val newComponents = listOf<AComponent<Any>>(newRoot.unsafeCast<AComponent<Any>>())
//
//            previousTree = newRoot
//            Reconciler.mergeComponents(oldComponents, newComponents, rootElement, null, newRoot)

            val end = Date.now()
            console.info("Render: ${end - start}ms")
        }

        fun init(rootElement: HTMLElement, onRender: Components.() -> Unit) {
            Visage.rootElement = rootElement
            Visage.onRender = onRender
            this.setUpHtml()
        }

        private fun setUpHtml() {
            // inject required styles
            Css.createBlock("*") {
                fontFamily = "'Quicksand', sans-serif"
                boxSizing = "border-box"
            }
            Css.createBlock(".vsb::-webkit-scrollbar") {
                width = "0px"
            }
            Css.createBlock("html, body") {
                width = "100%"
                height = "100%"
                padding = "0px"
                margin = "0px"
            }

            // inject required fonts
            val head = window.document.getElementsByTagName("head")!![0]!!

            val materialIcons = window.document.createElement("link") as HTMLLinkElement
            materialIcons.href = "https://fonts.googleapis.com/icon?family=Material+Icons"
            materialIcons.rel = "stylesheet"
            head.appendChild(materialIcons)

            val quickSand = window.document.createElement("link") as HTMLLinkElement
            quickSand.href = "https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;600;700&display=swap"
            quickSand.rel = "stylesheet"
            head.appendChild(quickSand)
        }

        fun render() {
            renderRunOnce.run()
        }

        fun rerender() {
            if (tree != null) {
                tree!!._invalid = true
            }
            render()
        }

    }

}
