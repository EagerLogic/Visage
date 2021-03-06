package com.el.vds.components

import kotlinx.browser.window
import org.w3c.dom.HTMLInputElement
import visage.core.AComponent
import visage.core.Components
import visage.core.IdGenerator
import visage.dom.*
import visage.ds.colorpalette.Skin
import visage.ds.forms.FieldModel
import visage.ds.utils.EFontWeight

class CTextField(val label: String, val model: FieldModel<String>) : AComponent<CTextField.Companion.State>() {

    companion object {
        class State {
            var focused = false
            val inputId = "tf-${IdGenerator.nextId}"
        }
    }

    var infoText: String? = null
    var disabled: Boolean = false
    var password: Boolean = false
    var onChange: Listener<String>? = null

    override fun initState(): State {
        return State()
    }

    override fun onComponentDidMount() {
        window.setTimeout({
            this.updateFocusedState()
        }, 100)
    }

    private fun updateFocusedState() {
        val inp = window.document.getElementById(this.state.inputId) as HTMLInputElement
        val isFocused = window.document.activeElement === inp
        if (this.state.focused != isFocused) {
            this.state.focused = isFocused
            this.refresh()
        }
    }

    override fun Components.render(children: List<AComponent<*>>) {
        var rootExtStyle = rootNormalStyle
        var smallLabelExtStyle = normalSmallLabelStyle
        if (this@CTextField.disabled) {
            rootExtStyle = rootDisabledStyle
            smallLabelExtStyle = disabledSmallLabelStyle
        } else if (this@CTextField.state.focused) {
            rootExtStyle = rootFocusedStyle
            smallLabelExtStyle = focusedSmallLabelStyle
            if (this@CTextField.model.error != null) {
                smallLabelExtStyle = errorSmallLabelStyle
            }
        } else if (this@CTextField.model.error != null) {
            rootExtStyle = rootErrorStyle
            smallLabelExtStyle = errorSmallLabelStyle
        }

        div {
            classes = "$rootStyle $rootExtStyle"

            div {
                classes = "$baseSmallLabelStyle $smallLabelExtStyle"

                text(this@CTextField.label)
            }
            div {
                style.apply { width = "100%" }

                tag("input") {
                    id = this@CTextField.state.inputId
                    attr["type"] = if (this@CTextField.password) "password" else "text"
                    attr["value"] = this@CTextField.model.value
                    if (this@CTextField.disabled) {
                        attr["disabled"] = "true"
                    }
                    classes = inputStyle

                    events["focus"] = {
                        this@CTextField.updateFocusedState()
                    }
                    events["blur"] = {
                        this@CTextField.updateFocusedState()
                    }
                }
            }
        }

        if (this@CTextField.model.error != null) {
            div {
                classes = "$infoTextBaseStyle $infoTextErrorStyle"

                text(this@CTextField.model.error!!)
            }
        } else if (this@CTextField.infoText != null) {
            div {
                classes = "$infoTextBaseStyle $infoTextNormalStyle"

                text(this@CTextField.infoText!!)
            }
        }
    }

}

private val rootStyle = Css.createClass {
    position = "relative"
    width = "100%"
    padding = "8px"
}

private val rootNormalStyle = Css.createClass {
    backgroundColor = Skin.palette.fieldNormalBgColor
    borderBottomStyle = "solid"
    borderBottomWidth = "2px"
    borderBottomColor = Skin.palette.fieldNormalBorderColor
}

private val rootDisabledStyle = Css.createClass {
    backgroundColor = Skin.palette.fieldDisabledBgColor
    borderBottomStyle = "solid"
    borderBottomWidth = "2px"
    borderBottomColor = Skin.palette.fieldDisabledBorderColor
}

private val rootFocusedStyle = Css.createClass {
    backgroundColor = Skin.palette.fieldNormalBgColor
    borderBottomStyle = "solid"
    borderBottomWidth = "2px"
    borderBottomColor = Skin.palette.fieldFocusedBorderColor
}

private val rootErrorStyle = Css.createClass {
    backgroundColor = Skin.palette.fieldNormalBgColor
    borderBottomStyle = "solid"
    borderBottomWidth = "2px"
    borderBottomColor = Skin.palette.fieldErrorBorderColor
}

private val baseSmallLabelStyle = Css.createClass {
    fontSize = "12px"
    fontWeight = EFontWeight.SemiBold.cssValue
    width = "100%"
    maxWidth = width
    overflow = "hidden"
    textOverflow = "ellipsis"
}

private val focusedSmallLabelStyle = Css.createClass {
    color = Skin.palette.primaryColor
}

private val normalSmallLabelStyle = Css.createClass {
    color = Skin.palette.textWeak
}

private val errorSmallLabelStyle = Css.createClass {
    color = Skin.palette.fieldErrorBorderColor
}

private val disabledSmallLabelStyle = Css.createClass {
    color = Skin.palette.textWeak
}

private val inputStyle = Css.createClass {
    width = "100%"
    backgroundColor = "transparent"
    borderWidth = "0px"
    padding = "2px 0px 0px 0px"
    fontSize = "16px"
    fontWeight = EFontWeight.Regular.cssValue
    color = Skin.palette.textStrong
}

private val infoTextBaseStyle = Css.createClass {
    width = "100%"
    maxWidth = "100%"
    fontSize = "12px"
    fontWeight = EFontWeight.Medium.cssValue
    padding = "4px 8px 0px 8px"
}

private val infoTextNormalStyle = Css.createClass {
    color = Skin.palette.textMedium
}

private val infoTextErrorStyle = Css.createClass {
    color = Skin.palette.errorColor
}


fun Components.TextField(label: String, model: FieldModel<String>, init: CTextField.() -> Unit = { }) =
    this.registerComponent(CTextField(label, model), init)