package com.el.vds.components

import org.w3c.dom.HTMLInputElement
import visage.core.Components
import visage.dom.*
import visage.ds.components.AInputBase
import visage.ds.forms.FieldModel

fun Components.TextField(label: String, model: FieldModel<String>, init: CTextField.() -> Unit = { }) =
    this.registerComponent(CTextField(label, model), init)

class CTextField(label: String, model: FieldModel<String>) : AInputBase<String, Unit>(label, model) {

    companion object {
        init {
            Css.createBlock("input:focus") {
                this["outline"] = "none"
            }
        }
    }


    var password = false

    override fun Components.renderInput() {
        tag("input") {
            id = this@CTextField.inputId
            attr["type"] = if (this@CTextField.password) "password" else "text"
            attr["value"] = this@CTextField.model.value
            if (this@CTextField.disabled) {
                attr["disabled"] = "true"
            }
            classes = inputStyle

            events["focus"] = {
                this@CTextField.handleFocusChanged()
            }
            events["blur"] = {
                this@CTextField.handleFocusChanged()
            }
            events["input"] = {
                val evt = it
                this@CTextField.handleValueChange((evt.target as HTMLInputElement).value)
            }
        }
    }

}




