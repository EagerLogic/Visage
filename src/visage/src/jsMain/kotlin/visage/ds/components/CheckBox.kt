package visage.ds.components

import org.w3c.dom.events.MouseEvent
import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.CssClass
import visage.dom.Listener
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.forms.FieldModel

fun Components.CheckBox(text: String, model: FieldModel<Boolean>, init: (CCheckBox.() -> Unit) = {}) = this.registerComponent(CCheckBox(text, model), init)

class CCheckBox(val text: String, val model: FieldModel<Boolean>) : APureComponent() {

    var disabled = false

    var onChange: Listener<Boolean>? = null

    private fun handleClick(evt: MouseEvent) {
        model.value = !model.value
        this.refresh()
        if (onChange != null) {
            onChange!!(model.value)
        }
    }

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = rootStyle
            if (!this@CCheckBox.disabled) {
                style.cursor = "pointer"
                events.onClick = this@CCheckBox::handleClick
            } else {
                style.opacity = "0.5"
            }

            div {
                classes = checkboxContainerStyle
                Icon(
                    if (this@CCheckBox.model.value) {
                        "check_box"
                    } else {
                        "check_box_outline_blank"
                    },
                    if (this@CCheckBox.model.error != null) {
                        Skin.palette.errorColor
                    } else if (this@CCheckBox.model.value) {
                        Skin.palette.primaryColor
                    } else {
                        Skin.palette.secondaryColor
                    },
                    22
                )
            }
            div {
                style.width = "8px"
                style.minWidth = style.width
            }
            div {
                classes = textContainerStyle
                if (this@CCheckBox.model.error != null) {
                    style.color = Skin.palette.errorColor
                }

                +this@CCheckBox.text
            }
        }
        if (this@CCheckBox.model.error != null) {
            div {
                style.apply {
                    width = "100%"
                    maxWidth = "100%"
                    overflow = "hidden"
                    padding = "4px 0px 0px 0px"
                }

                Text(this@CCheckBox.model.error!!, FieldTextStyles.errorMessageStyle)
            }
        }
    }

}

private val rootStyle by CssClass {
    width = "100%"
    maxWidth = "100%"
    display = "flex"
    alignItems = "center"
}

private val checkboxContainerStyle by CssClass {
    fontSize = "14px"
    display = "inline-flex"
    alignItems = "center"
    justifyContent = "center"
}

private val textContainerStyle by CssClass {
    flex = "1"
    fontSize = "14px"
    color = Skin.palette.normalTextColor
}