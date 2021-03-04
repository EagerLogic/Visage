package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.Css
import visage.dom.div
import visage.dom.text

class CCodeBlock(val code: String) : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        div {
            attr.classes = codeBlockStyle

            text(this@CCodeBlock.code)
        }
    }
}

private val codeBlockStyle = Css.createClass {
    width = "100%"
    padding = "8px"
    backgroundColor = "#f2f2f2"
    color = "#333"
    whiteSpace = "pre"
    fontSize = "14px"
    fontFamily = "monospace, monospace"
}

fun Components.CodeBlock(code: String) = this.registerComponent(CCodeBlock(code), {})