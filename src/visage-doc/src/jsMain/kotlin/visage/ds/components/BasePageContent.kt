package visage.ds.components

import visage.core.AComponent
import visage.core.APureComposite
import visage.core.Components
import visage.dom.Css
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight

class CBasePageContent(
    val title: String
) : APureComposite() {

    var isLoading: Boolean = false

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = baseContainerStyle
            if (this@CBasePageContent.isLoading) {
                style.apply {
                    opacity = "0.5"
                    pointerEvents = "none"
                }
            }

            div {
                classes = titleContainerStyle

                div {
                    classes = titleTextStyle

                    + this@CBasePageContent.title
                }
            }

            if (this@CBasePageContent.isLoading) {
                HorizontalLoadIndicator(null)
            }

            div {
                classes = contentWrapperStyle

                div {
                    classes = contentStyle

                    children.forEach {
                        +it
                    }
                }
            }


        }
    }

}

fun Components.BasePageContent(title: String, init: CBasePageContent.() -> Unit) =
    this.registerComponent(CBasePageContent(title), init)

private val baseContainerStyle = Css.createClass {
    width = "100%"
    height = "100%"
    overflow = "hidden"
    display = "flex"
    flexDirection = "column"
}

private val titleContainerStyle = Css.createClass {
    width = "100%"
    display = "flex"
    alignItems = "center"
    justifyContent = "flexStart"
    height = "64px"
    minHeight = height
    maxHeight = height
    backgroundColor = Skin.palette.headerBgColor
    overflow = "hidden"
    paddingLeft = "16px"
    paddingRight = "16px"
}

private val titleTextStyle = Css.createClass {
    fontSize = "20px"
    fontWeight = EFontWeight.SemiBold.cssValue
    color = Skin.palette.headerTextColor
}

private val contentWrapperStyle = Css.createClass {
    width = "100%"
    flexGrow = "1"
    overflow = "hidden"
}

private val contentStyle = Css.createClass {
    overflow = "auto"
    width = "100%"
    minWidth = "100%"
    maxWidth = "100%"
    height = "100%"
    minHeight = "100%"
    maxHeight = "100%"
    padding = "32px"
}