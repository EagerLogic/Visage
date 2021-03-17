package visage.ds.components

import visage.core.AComponent
import visage.core.APureComposite
import visage.core.Components
import visage.dom.CssClass
import visage.dom.div
import visage.ds.utils.RenderMode

fun Components.ResponsiveVBox(init: CResponsiveVBox.() -> Unit) = this.registerComponent(CResponsiveVBox(), init)

class CResponsiveVBox : APureComposite() {
    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = if (RenderMode.compressed) verticalRootStyle else horizontalRootStyle

            children.forEachIndexed { index, child ->
                if (index > 0) {
                    div {
                        style.apply {
                            width = "32px"
                            minWidth = width
                            maxWidth = width
                            height = "32px"
                            minHeight = height
                            maxHeight = height
                        }
                    }
                }

                div {
                    style.apply {
                        display = "inline-flex"
                        flex = "1"
                        flexBasis = "0px"
                    }

                    +child
                }
            }
        }
    }
}

private val horizontalRootStyle by CssClass {
    display = "flex"
    width = "100%"
    minWidth = "100%"
    maxWidth = "100%"
    flexDirection = "row"
    alignItems = "stretch"
}

private val verticalRootStyle by CssClass {
    display = "flex"
    width = "100%"
    minWidth = "100%"
    maxWidth = "100%"
    flexDirection = "column"
}