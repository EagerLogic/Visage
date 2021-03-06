package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.CssClass
import visage.dom.TagStyles
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight

class CButton(var text: String?, var icon: String?) : APureComponent() {

    init {
        if (text == null && icon == null) {
            throw IllegalArgumentException("The button must have at least an icon or a text, but given null for both parameters!")
        }
    }

    var variant: EButtonVariant = EButtonVariant.Filled
    var size: EButtonSize = EButtonSize.Normal
    var color: EButtonColor = EButtonColor.Primary
    var disabled: Boolean = false
    var onClick: (() -> Unit)? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = buttonBaseClass
            classes += " " + if (this@CButton.disabled) disabledButtonBaseClass else enabledButtonBaseClass
            classes += " " + this@CButton.size.styleClass
            style.merge(createButtonStyle(this@CButton.variant, this@CButton.color))

            if (this@CButton.variant == EButtonVariant.Link) {
                style.paddingLeft = "2px"
                style.paddingRight = "2px"
            } else if (this@CButton.text == null) {
                style.paddingLeft = when (this@CButton.size) {
                    EButtonSize.Small -> "8px"
                    EButtonSize.Normal -> "12px"
                    EButtonSize.Large -> "14px"
                }
                style.paddingRight = style.paddingLeft
            }

            if (!this@CButton.disabled && this@CButton.onClick != null) {
                events.onClick = { this@CButton.onClick?.invoke() }
            }

            if (this@CButton.icon != null) {
                div {
                    classes = "material-icons"
                    style.apply {
                        display = "inline-flex"
                        alignItems = "center"
                        justifyContent = "center"
                        fontSize = when (this@CButton.size) {
                            EButtonSize.Small -> "18px"
                            EButtonSize.Normal -> "22px"
                            EButtonSize.Large -> "26px"
                        }
                        flexGrow = "0"
                        flexShrink = "0"
                    }

                    +this@CButton.icon!!
                }
            }

            if (this@CButton.icon != null && this@CButton.text != null) {
                div {
                    style.apply {
                        width = "4px"
                        minWidth = "4px"
                        maxWidth = "4px"
                    }
                }
            }

            if (this@CButton.text != null) {
                div {
                    style.apply {
                        display = "inline-flex"
                        alignItems = "center"
                        justifyContent = "center"
                        flexGrow = "0"
                        flexShrink = "0"
                    }

                    +this@CButton.text!!
                }
            }
        }
    }

}

private val buttonBaseClass by CssClass {
    display = "inline-flex"
    alignItems = "stretch"
    justifyContent = "center"
    flexDirection = "row"
}

private val enabledButtonBaseClass by CssClass {
    cursor = "pointer"
    pseudo {
        hover {
            opacity = "0.75"
        }
        active {
            opacity = "0.5"
        }
    }
}

private val disabledButtonBaseClass by CssClass {
    opacity = "0.4"
}


enum class EButtonSize(val styleClass: String) {
    Small(smallButtonStyleClass),
    Normal(normalButtonStyleClass),
    Large((largeButtonStyleClass))
}

private val smallButtonStyleClass by CssClass {
    paddingLeft = "20px"
    paddingRight = "20px"
    height = "32px"
    fontSize = "12px"
    fontWeight = EFontWeight.Regular.cssValue
    borderRadius = "4px"
}

private val normalButtonStyleClass by CssClass {
    paddingLeft = "24px"
    paddingRight = "24px"
    height = "40px"
    fontSize = "14px"
    fontWeight = EFontWeight.SemiBold.cssValue
    borderRadius = "4px"
}

private val largeButtonStyleClass by CssClass {
    paddingLeft = "32px"
    paddingRight = "32px"
    height = "48px"
    fontSize = "16px"
    fontWeight = EFontWeight.SemiBold.cssValue
    borderRadius = "4px"
}


enum class EButtonVariant {

    Filled,
    Outlined,
    Link

}

enum class EButtonColor(private val getter: () -> String) {

    Primary({ Skin.palette.primaryColor }),
    Secondary({ Skin.palette.secondaryColor }),
    Success({ Skin.palette.successColor }),
    Warning({ Skin.palette.warningColor }),
    Danger({ Skin.palette.dangerColor });

    val normalColor: String
        get() {
            return getter()
        }


}

private fun createButtonStyle(variant: EButtonVariant, buttonColor: EButtonColor): TagStyles {
    return when (variant) {
        EButtonVariant.Filled -> TagStyles().apply {
            backgroundColor = buttonColor.normalColor
            color = Skin.palette.negativeStrongTextColor
            border = "2px solid transparent"
        }
        EButtonVariant.Outlined -> TagStyles().apply {
            backgroundColor = "transparent"
            color = buttonColor.normalColor
            border = "2px solid ${buttonColor.normalColor}"
        }
        EButtonVariant.Link -> TagStyles().apply {
            backgroundColor = "transparent"
            color = buttonColor.normalColor
            border = "2px solid transparent"
        }
    }
}

fun Components.ButtonWithIcon(text: String, icon: String, init: (CButton.() -> Unit) = {}) =
    this.registerComponent(CButton(text, icon), init)

fun Components.Button(text: String, init: (CButton.() -> Unit) = {}) =
    this.registerComponent(CButton(text, null), init)

fun Components.IconButton(icon: String, init: (CButton.() -> Unit) = {}) =
    this.registerComponent(CButton(null, icon), init)