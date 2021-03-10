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
    paddingLeft = "24px"
    paddingRight = "24px"
    paddingTop = "8px"
    paddingBottom = "8px"
    fontSize = "13px"
    fontWeight = EFontWeight.Regular.cssValue
    borderRadius = "3px"
}

private val normalButtonStyleClass = Css.createClass {
    paddingLeft = "32px"
    paddingRight = "32px"
    paddingTop = "12px"
    paddingBottom = "12px"
    fontSize = "15px"
    fontWeight = EFontWeight.SemiBold.cssValue
    borderRadius = "4px"
}

private val largeButtonStyleClass = Css.createClass {
    paddingLeft = "40px"
    paddingRight = "40px"
    paddingTop = "14px"
    paddingBottom = "14px"
    fontSize = "16px"
    fontWeight = EFontWeight.SemiBold.cssValue
    borderRadius = "6px"
}



enum class EButtonVariant {

    Filled,
    Outlined,
    Link

}

enum class EButtonColor(val normalColor: String) {
    Default(normalColor = Skin.palette.primaryColor),
    Success(normalColor = Skin.palette.successColor),
    Warning(normalColor = Skin.palette.warningColor),
    Danger(normalColor = Skin.palette.dangerColor)
}

private fun createButtonStyleClass(variant: EButtonVariant, buttonColor: EButtonColor): String {
    return when (variant) {
        EButtonVariant.Filled -> Css.createClass {
            backgroundColor = buttonColor.normalColor
            color = Skin.palette.lightTextStrong
        }
        EButtonVariant.Outlined -> Css.createClass {
            backgroundColor = "transparent"
            borderColor = buttonColor.normalColor
            borderWidth = "2px"
            borderStyle = "solid"
            color = buttonColor.normalColor
        }
        EButtonVariant.Link -> Css.createClass {
            backgroundColor = "transparent"
            color = buttonColor.normalColor
        }
    }
}

fun Components.Button(text: String, init: (CButton.() -> Unit) = {}) =
    this.registerComponent(CButton(text), init);