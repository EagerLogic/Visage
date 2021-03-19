package visage.ds.components

import kotlinx.browser.window
import visage.core.AComponent
import visage.core.Components
import visage.core.IdGenerator
import visage.dom.*
import visage.ds.colorpalette.Skin
import visage.ds.forms.AFieldModel
import visage.ds.utils.EFontWeight
import visage.ds.utils.ETextAlign

abstract class AInputBase<GValue, GMeta>(val label: String, val model: AFieldModel<GValue>) : AComponent<AInputBase.Companion.State>() {

    companion object {
        class State {
            var focused = false
            val inputId = "inp-${IdGenerator.nextId}"
        }

        protected val inputStyle by CssClass {
            width = "100%"
            minWidth = "100%"
            maxWidth = "100%"
            backgroundColor = "transparent"
            borderWidth = "0px"
            padding = "2px 0px 0px 0px"
            fontSize = "16px"
            fontWeight = EFontWeight.Regular.cssValue
            color = Skin.palette.normalTextColor
        }
    }

    var infoText: String? = null
    var disabled: Boolean = false
    var onChange: Listener<GValue>? = null

    override fun initState(): State {
        return State()
    }

    override fun onComponentDidMount() {
        window.setTimeout({
            this.updateFocusedState()
        }, 100)
    }

    protected val inputId: String
        get() {
            return this.state.inputId
        }

    private fun updateFocusedState() {
        val inp = window.document.getElementById(this.state.inputId)
        val isFocused = window.document.activeElement === inp
        if (this.state.focused != isFocused) {
            this.state.focused = isFocused
            this.refresh()
        }
    }

    protected fun handleFocusChanged() {
        this.updateFocusedState()
    }

    protected fun handleValueChange(newValue: GValue) {
        this.model.value = newValue
        if (this.onChange != null) {
            this.onChange!!(newValue)
        }
        this.refresh()
    }

    final override fun Components.render(children: List<AComponent<*>>) {
        var rootExtStyle = rootNormalStyle
        if (this@AInputBase.state.focused) {
            rootExtStyle = rootFocusedStyle
        } else if (this@AInputBase.model.error != null) {
            rootExtStyle = rootErrorStyle
        }

        val labelStyle = if (this@AInputBase.model.error != null) {
            FieldTextStyles.errorLabelStyle
        } else if (this@AInputBase.state.focused) {
            FieldTextStyles.focusedLabelStyle
        } else {
            FieldTextStyles.normalLabelStyle
        }

        div {
            classes = "$rootStyle $rootExtStyle"
            if (this@AInputBase.disabled) {
                style.opacity = "0.5"
                attr["disabled"] = "true"
            }

            Text(this@AInputBase.label, labelStyle)

            div {
                style.apply { width = "100%" }

                this@AInputBase.doRenderInput(this)
            }
        }

        if (this@AInputBase.model.error != null) {
            div {
                classes = "$infoTextBaseStyle"

                Text(this@AInputBase.model.error!!, FieldTextStyles.errorMessageStyle)
            }
        } else if (this@AInputBase.infoText != null) {
            div {
                classes = "$infoTextBaseStyle"

                Text(this@AInputBase.infoText!!, FieldTextStyles.infoTextStyle)
            }
        }
    }

    private fun doRenderInput(parent: Components) {
        parent.renderInput()
    }

    protected abstract fun Components.renderInput()

}

object FieldTextStyles {
    val normalLabelStyle: TextStyle
        get() {
            return TextStyle(
                12,
                EFontWeight.SemiBold,
                Skin.palette.weakTextColor,
                false
            )
        }
    val focusedLabelStyle: TextStyle
        get() {
            return TextStyle(
                12,
                EFontWeight.SemiBold,
                Skin.palette.primaryColor,
                false
            )
        }
    val errorLabelStyle: TextStyle
        get() {
            return TextStyle(
                12,
                EFontWeight.SemiBold,
                Skin.palette.errorColor,
                false
            )
        }
    val infoTextStyle: TextStyle
        get() {
            return TextStyle(
                12,
                EFontWeight.Regular,
                Skin.palette.weakTextColor,
                false
            )
        }
    val errorMessageStyle: TextStyle
        get() {
            return TextStyle(
                12,
                EFontWeight.Regular,
                Skin.palette.errorColor,
                false
            )
        }
}

private val rootStyle by CssClass {
    position = "relative"
    width = "100%"
    padding = "8px"
}

private val rootNormalStyle by CssClass {
    backgroundColor = Skin.palette.fieldBgColor
    borderBottomStyle = "solid"
    borderBottomWidth = "2px"
    borderBottomColor = Skin.palette.strongSeparatorColor
}

private val rootFocusedStyle by CssClass {
    backgroundColor = Skin.palette.fieldBgColor
    borderBottomStyle = "solid"
    borderBottomWidth = "2px"
    borderBottomColor = Skin.palette.primaryColor
}

private val rootErrorStyle by CssClass {
    backgroundColor = Skin.palette.fieldBgColor
    borderBottomStyle = "solid"
    borderBottomWidth = "2px"
    borderBottomColor = Skin.palette.errorColor
}

//private val baseSmallLabelStyle by CssClass {
//    fontSize = "12px"
//    fontWeight = EFontWeight.SemiBold.cssValue
//    width = "100%"
//    maxWidth = width
//    overflow = "hidden"
//    textOverflow = "ellipsis"
//}

//private val focusedSmallLabelStyle by CssClass {
//    color = Skin.palette.primaryColor
//}
//
//private val normalSmallLabelStyle by CssClass {
//    color = Skin.palette.textWeak
//}
//
//private val errorSmallLabelStyle by CssClass {
//    color = Skin.palette.fieldErrorBorderColor
//}
//
//private val disabledSmallLabelStyle by CssClass {
//    color = Skin.palette.textWeak
//}



val infoTextBaseStyle by CssClass {
    width = "100%"
    maxWidth = "100%"
    overflow = "hidden"
    padding = "4px 8px 0px 8px"
}
