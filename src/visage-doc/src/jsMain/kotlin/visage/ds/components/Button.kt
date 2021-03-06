package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.Css
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight

class CButton(var text: String) : APureComponent() {

    var variant: EButtonVariant = EButtonVariant.Filled
    var size: EButtonSize = EButtonSize.Normal
    var color: EButtonColor = EButtonColor.Default
    var disabled: Boolean = false
    var onClick: (() -> Unit)? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = if (this@CButton.disabled) disabledButtonBaseClass else enabledButtonBaseClass
            classes += " " + this@CButton.size.styleClass
            classes += " " + createButtonStyleClass(this@CButton.variant, this@CButton.color)

            if (!this@CButton.disabled && this@CButton.onClick != null) {
                events.onClick = { this@CButton.onClick?.invoke() }
            }

            +this@CButton.text
        }
    }

}

private val enabledButtonBaseClass = Css.createClass {
    display = "inline-flex"
    alignItems = "center"
    justifyContent = "center"
    flexDirection = "row"
    opacity = "1.0"
    cursor = "pointer"
    this["text-transform"] = "uppercase"
    pseudo {
        hover {
            opacity = "0.75"
        }
        active {
            opacity = "0.5"
        }
    }
}

private val disabledButtonBaseClass = Css.createClass {
    display = "inline-flex"
    alignItems = "center"
    justifyContent = "center"
    flexDirection = "row"
    opacity = "0.4"
}



enum class EButtonSize(val styleClass: String) {
    Small(smallButtonStyleClass),
    Normal(normalButtonStyleClass),
    Large((largeButtonStyleClass))
}

private val smallButtonStyleClass = Css.createClass {
    paddingLeft = "12px"
    paddingRight = "12px"
    paddingTop = "6px"
    paddingBottom = "6px"
    fontSize = "12px"
    fontWeight = EFontWeight.Regular.cssValue
    borderWidth = "2px"
    borderStyle = "solid"
}

private val normalButtonStyleClass = Css.createClass {
    paddingLeft = "12px"
    paddingRight = "12px"
    paddingTop = "6px"
    paddingBottom = "6px"
    fontSize = "14px"
    fontWeight = EFontWeight.SemiBold.cssValue
    borderWidth = "3px"
    borderStyle = "solid"
}

private val largeButtonStyleClass = Css.createClass {
    paddingLeft = "20px"
    paddingRight = "20px"
    paddingTop = "8px"
    paddingBottom = "8px"
    fontSize = "16px"
    fontWeight = EFontWeight.SemiBold.cssValue
    borderWidth = "4px"
    borderStyle = "solid"
}



enum class EButtonVariant {

    Filled,
    Outlined,
    Link

}

enum class EButtonColor(val normalColor: String, val darkColor: String) {
    Default(normalColor = Skin.palette.primaryColor, darkColor = Skin.palette.primaryDarkColor),
    Success(normalColor = Skin.palette.successColor, darkColor = Skin.palette.successDarkColor),
    Warning(normalColor = Skin.palette.warningColor, darkColor = Skin.palette.warningDarkColor),
    Danger(normalColor = Skin.palette.dangerColor, darkColor = Skin.palette.dangerDarkColor)
}

private fun createButtonStyleClass(variant: EButtonVariant, buttonColor: EButtonColor): String {
    return when (variant) {
        EButtonVariant.Filled -> Css.createClass {
            backgroundColor = buttonColor.normalColor
            borderColor = buttonColor.darkColor
            color = Skin.palette.lightTextStrong
        }
        EButtonVariant.Outlined -> Css.createClass {
            backgroundColor = "transparent"
            borderColor = buttonColor.normalColor
            color = buttonColor.normalColor
        }
        EButtonVariant.Link -> Css.createClass {
            backgroundColor = "transparent"
            borderColor = "transparent"
            color = buttonColor.normalColor
        }
    }
}

fun Components.Button(text: String, init: (CButton.() -> Unit) = {}) =
    this.registerComponent(CButton(text), init);