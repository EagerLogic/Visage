package visagedoc.scenes.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.Css
import visage.dom.div
import visage.ds.components.TextField
import visage.ds.components.page.Page
import visage.ds.forms.FieldModel
import visagedoc.components.*

class CTextFieldScene : APureComponent() {

    companion object {
        init {
            Css.createBlock("*:focus") {
                this["outline"] = "none"
            }
        }
    }

    override fun Components.render(children: List<AComponent<*>>) {
        Page {
            head("Components / TextField")
            body {
                DocPage {
                    title("Introduction")
                    p {
                        +"The TextField component is responsible to get some textual information from the user."
                    }
                    p {
                        div {
                            style.width = "400px"
                            style.maxWidth = "100%"

                            TextField("Empty", FieldModel("")) {
                                infoText = "This is the infoText"
                            }

                            div { style.height = "32px" }

                            TextField("With value", FieldModel("Some value")) {
                                infoText = "This is the infoText"
                            }

                            div { style.height = "32px" }

                            val model = FieldModel("Some value")
                            model.error = "This is an error message"
                            TextField("With error", model) {
                                infoText = "This is the infoText"
                            }

                            div { style.height = "32px" }

                            TextField("Disabled", FieldModel("Some value")) {
                                disabled = true
                                infoText = "This is the infoText"
                            }




                        }
                    }
                    codeBlock("""
TextField("Empty", FieldModel("", Unit)) {
    infoText = "This is the infoText"
}

TextField("With value", FieldModel("Some value", Unit)) {
    infoText = "This is the infoText"
}

val model = FieldModel("Some value", Unit)
model.error = "This is an error message"
TextField("With error", model) {
    infoText = "This is the infoText"
}

TextField("Disabled", FieldModel("Some value", Unit)) {
    disabled = true
    infoText = "This is the infoText"
}
                """)

                    properties {
                        prop("model", "The model of the TExtField. This class encapsulates the value and the validation result.")
                        prop("label", "The label of the TextField which is shown above the input.", "")
                        prop("infoText", "A small textual helper information describes the purpos of the data to help the uesr fill it correctly.")
                        prop("disabled", "If this property is true, than the TextField is readonly.", "false")
                        prop("password", "Set it to true, to make the TextField a password input.", "false")
                        prop("onChange", "This event is fired every time when the content of the TextField is changed.", "null")
                    }
                }
            }
        }
    }
}

fun Components.TextFieldScene() = this.registerComponent(CTextFieldScene(), {})