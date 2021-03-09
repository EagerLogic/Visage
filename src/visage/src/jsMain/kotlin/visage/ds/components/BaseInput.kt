package visage.ds.components

import kotlinx.browser.window
import visage.core.AComponent
import visage.core.Components
import visage.core.IdGenerator
import visage.dom.*
import visage.ds.colorpalette.Skin
import visage.ds.forms.AFieldModel
import visage.ds.utils.EFontWeight

abstract class AInputBase<GValue, GMeta>(val label: String, val model: AFieldModel<GValue>) : AComponent<AInputBase.Companion.State>() {

    companion object {
        class State {
            var focused = false
            val inputId = "inp-${IdGenerator.nextId}"
        }

        protected val inputStyle = Css.createClass {
            width = "100%"
            minWidth = "100%"
            maxWidth = "100%"
            backgroundColor = "transparent"
            borderWidth = "0px"
            padding = "2px 0px 0px 0px"
            fontSize = "16px"
            fontWeight = EFontWeight.Regular.cssValue
            color = Skin.palette.textStrong
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
        var smallLabelExtStyle = normalSmallLabelStyle
        if (this@AInputBase.disabled) {
            rootExtStyle = rootDisabledStyle
            smallLabelExtStyle = disabledSmallLabelStyle
        } else if (this@AInputBase.state.focused) {
            rootExtStyle = rootFocusedStyle
            smallLabelExtStyle = focusedSmallLabelStyle
            if (this@AInputBase.model.error != null) {
                smallLabelExtStyle = errorSmallLabelStyle
            }
        } else if (this@AInputBase.model.error != null) {
            rootExtStyle = rootErrorStyle
            smallLabelExtStyle = errorSmallLabelStyle
        }

        div {
            classes = "$rootStyle $rootExtStyle"
            //style.height = this@AInputBase.height ?: "100px"

            div {
                classes = "$baseSmallLabelStyle $smallLabelExtStyle"

                +this@AInputBase.label
            }
            div {
                style.apply { width = "100%" }

                this@AInputBase.doRenderInput(this)
            }
        }

        if (this@AInputBase.model.error != null) {
            div {
                classes = "$infoTextBaseStyle $infoTextErrorStyle"

                +this@AInputBase.model.error!!
            }
        } else if (this@AInputBase.infoText != null) {
            div {
                classes = "$infoTextBaseStyle $infoTextNormalStyle"

                +this@AInputBase.infoText!!
            }
        }
    }

    private fun doRenderInput(parent: Components) {
        parent.renderInput()
    }

    protected abstract fun Components.renderInput()

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



val infoTextBaseStyle = Css.createClass {
    width = "100%"
    maxWidth = "100%"
    fontSize = "12px"
    fontWeight = EFontWeight.Medium.cssValue
    padding = "4px 8px 0px 8px"
}

private val infoTextNormalStyle = Css.createClass {
    color = Skin.palette.textMedium
}

val infoTextErrorStyle = Css.createClass {
    color = Skin.palette.errorColor
}
