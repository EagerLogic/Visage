package visage.core

import org.w3c.dom.HTMLElement
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

        }

        fun render() {
            renderRunOnce.run()
        }

        fun rerender() {
            if (tree != null) {
                tree!!.refresh()
            }
            render()
        }

    }

}
