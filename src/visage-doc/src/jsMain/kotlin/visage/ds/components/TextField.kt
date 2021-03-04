package com.el.vds.components

import visage.ds.colorpalette.Skin
import org.w3c.dom.HTMLInputElement
import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.Css
import visage.dom.Listener
import visage.dom.div
import visage.dom.tag
import visage.ds.components.ETextStyles
import visage.ds.components.Text
import visage.ds.forms.FieldModel

class CTextField(val model: FieldModel<String>) : APureComponent() {

    var label: String? = null
    var infoText: String? = null
    var disabled: Boolean = false
    var password: Boolean = false
    var onChange: Listener<String>? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {

            if (this@CTextField.label != null) {
                div {
                    Text(this@CTextField.label!!, ETextStyles.FieldLabel)
                }
                div { style.apply { height = "2px" } }
            }


            div {
                tag("input") {
                    attr["type"] = if (this@CTextField.password) "password" else "text"
                    attr.classes = baseInputStyle + " " + if (this@CTextField.disabled) {
                        disabledInputStyle
                    } else if (this@CTextField.model.error != null) {
                        errorInputStyle
                    } else {
                        normalInputStyle
                    }
                    attr["value"] = this@CTextField.model.value

                    events["input"] = {
                        val newValue = (it.target as HTMLInputElement).value
                        this@CTextField.model.value = newValue
                        this@CTextField.onChange?.invoke(newValue)
                    }
                }
            }

            if (this@CTextField.model.error != null) {
                div { style.apply { height = "2px" } }
                div {
                    Text(this@CTextField.model.error!!, ETextStyles.FieldErrorText)
                }
            } else if (this@CTextField.infoText != null) {
                div { style.apply { height = "2px" } }
                div {
                    Text(this@CTextField.infoText!!, ETextStyles.FieldInfoText)
                }
            }
        }
    }

}

private val baseInputStyle = Css.createClass {
    width = "100%"
    padding = "8px"
    borderWidth = "2px"
    borderStyle = "solid"
    fontSize = "${ETextStyles.FieldValue.textStyle.fontSize}px"
    fontWeight = "${ETextStyles.FieldValue.textStyle.fontWeight}"
    color = ETextStyles.FieldValue.textStyle.color
    borderRadius = "4px"

}

private val normalInputStyle = Css.createClass {
    backgroundColor = Skin.palette.fieldNormalBgColor;
    borderColor = Skin.palette.fieldNormalBorderColor;
    pseudo {
        focus {
            this["outline"] = "none"
            borderColor = Skin.palette.fieldSelectedBorderColor
        }
    }
}

private val disabledInputStyle = Css.createClass {
    backgroundColor = Skin.palette.fieldDisabledBgColor;
    borderColor = Skin.palette.fieldDisabledBorderColor;
    pseudo {
        focus {
            this["outline"] = "none"
        }
    }
}

private val errorInputStyle = Css.createClass {
    backgroundColor = Skin.palette.fieldErrorBgColor;
    borderColor = Skin.palette.fieldErrorBorderColor;
    pseudo {
        focus {
            this["outline"] = "none"
        }
    }
}

fun Components.TextField(model: FieldModel<String>, init: CTextField.() -> Unit = {  }) = this.registerComponent(CTextField(model), init)