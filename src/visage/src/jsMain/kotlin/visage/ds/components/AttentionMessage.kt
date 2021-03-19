package visage.ds.components

import visage.core.Components
import visage.dom.CssClass
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight

fun Components.AttentionMessage(type: EAttentionMessageType, message: String) = this.registerFunctionalComponent({}) {
    div {
        classes = "$rootStyle ${type.rootStyle}"

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

private val rootStyle by CssClass {
    width = "100%"
    maxWidth = width
    padding = "8px"
}

private val rootInfoStyle by CssClass {
    borderLeft = "8px solid ${Skin.palette.primaryColor}"
    backgroundColor = "${Skin.palette.primaryColor}60"
}

private val rootSuccessStyle by CssClass {
    borderLeft = "8px solid ${Skin.palette.successColor}"
    backgroundColor = "${Skin.palette.successColor}60"
}

private val rootWarningStyle by CssClass {
    borderLeft = "8px solid ${Skin.palette.warningColor}"
    backgroundColor = "${Skin.palette.warningColor}60"
}

private val rootDangerStyle by CssClass {
    borderLeft = "8px solid ${Skin.palette.dangerColor}"
    backgroundColor = "${Skin.palette.dangerColor}60"
}

private val messageStyle by CssClass {
    fontSize = "14px"
    fontWeight = EFontWeight.Regular.cssValue
    color = Skin.palette.normalTextColor
}