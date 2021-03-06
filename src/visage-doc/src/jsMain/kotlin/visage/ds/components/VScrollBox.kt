package visage.ds.components

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import visage.core.*
import visage.dom.Css
import visage.dom.div

class CVScrollBox(val height: String) : AComposite<CVScrollBox.Companion.State>() {

    companion object {

        class State {
            val id = "vsg-${IdGenerator.nextId}"
            var scrollTop = 0
            var scrollHeight = 20
            var scrollVisible = false
        }
    }

    var padding: String = "16px"
    var lightScrollThumb: Boolean = false

    override fun initState(): State {
        return State()
    }

    private fun refreshThumbSize() {
        val sb = document.getElementById(this.state.id) as HTMLDivElement
        val sh = sb.scrollHeight
        val ch = sb.clientHeight
        val st = sb.scrollTop
        if (sh <= ch) {
            this.state.scrollTop = 0
            this.state.scrollHeight = 20
            this.state.scrollVisible = false
        } else {
            this.state.scrollTop = ((ch / sh.toFloat()) * st).toInt()
            this.state.scrollHeight = ((ch / sh.toFloat()) * ch).toInt()
            this.state.scrollVisible = true
        }

        this.refresh()
    }

    override fun Components.render(children: List<AComponent<*>>) {
        val state = this@CVScrollBox.state
        div {
            classes = rootStyle
            style.apply {
                height = this@CVScrollBox.height
                minHeight = height
                maxHeight = height
            }

            div {
                classes = "vsb $hiddenScrollStyle"
                id = state.id
                events["scroll"] = {this@CVScrollBox.refreshThumbSize()}

                div {
                    classes = contentBoxStyle
                    style.apply { padding = this@CVScrollBox.padding }

                    children.forEach {
                        this.addChild(it)
                    }
                }
            }

            div {
                classes = scrollThumbStyle
                style.apply {
                    backgroundColor = if (this@CVScrollBox.lightScrollThumb) "rgba(255,255,255, 0.3)" else "rgba(0,0,0, 0.3)"
                    height = "${state.scrollHeight}px"
                    top = "${state.scrollTop}px"
                    visibility = if (state.scrollVisible) "visible" else "hidden"
                }
            }
        }
    }

}

private val rootStyle = Css.createClass {
    width = "100%"
    minWidth = "100%"
    maxWidth = "100%"
    overflow = "hidden"
    position = "relative"
}

private val hiddenScrollStyle = Css.createClass {
    this["scrollbar-width"] = "none"
    width = "200%"
    minWidth = width
    maxWidth = width
    height = "100%"
    minHeight = height
    maxHeight = height
    overflowX = "hidden"
    overflowY = "scroll"
}

private val contentBoxStyle = Css.createClass {
    width = "50%"
    minWidth = width
    maxWidth = width
    minHeight = "100%"
    overflowX = "hidden"
    overflowY = "visible"
}

private val scrollThumbStyle = Css.createClass {
    position = "absolute"
    width = "4px"
    minHeight = "20px"
    maxHeight = "100%"
    right = "2px"
    top = "0px"
    borderRadius = "2px"
}

fun Components.VScrollBox(height: String, init: CVScrollBox.() -> Unit) = this.registerComponent(CVScrollBox(height), init)