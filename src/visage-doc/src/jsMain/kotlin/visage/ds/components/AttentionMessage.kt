package visage.ds.components

import visage.core.Components
import visage.dom.Css
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight

fun Components.AttentionMessage(type: EAttentionMessageType, title: String, message: String) = this.registerFunctionalComponent({}) {
    div {
        classes = "$rootStyle ${type.rootStyle}"

        div {
            classes = titleStyle

            +title
        }
        div {
            classes = messageStyle

            +message
        }
    }
}

enum class EAttentionMessageType(val rootStyle: String) {
    Info(rootInfoStyle),
    Success(rootSuccessStyle),
    Warning(rootWarningStyle),
    Danger(rootDangerStyle)

}

private val rootStyle = Css.createClass {
    width = "100%"
    maxWidth = width
    padding = "16px"
}

private val rootInfoStyle = Css.createClass {
    borderLeft = "8px solid ${Skin.palette.primaryColor}"
    backgroundColor = Skin.palette.primaryLightColor
}

private val rootSuccessStyle = Css.createClass {
    borderLeft = "8px solid ${Skin.palette.successColor}"
    backgroundColor = Skin.palette.successLightColor
}

private val rootWarningStyle = Css.createClass {
    borderLeft = "8px solid ${Skin.palette.warningColor}"
    backgroundColor = Skin.palette.warningLightColor
}

private val rootDangerStyle = Css.createClass {
    borderLeft = "8px solid ${Skin.palette.dangerColor}"
    backgroundColor = Skin.palette.dangerLightColor
}

private val titleStyle = Css.createClass {
    fontSize = "16px"
    fontWeight = EFontWeight.SemiBold.cssValue
    color = "#222"
}

private val messageStyle = Css.createClass {
    fontSize = "14px"
    fontWeight = EFontWeight.Regular.cssValue
    color = "#444"
}