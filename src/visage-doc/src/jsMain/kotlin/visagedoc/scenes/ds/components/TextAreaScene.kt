package visagedoc.scenes.ds.components

import visage.core.Components
import visage.dom.div
import visage.ds.components.TextArea
import visage.ds.components.page.Page
import visage.ds.forms.FieldModel
import visagedoc.components.DocPage
import visagedoc.components.p
import visagedoc.components.title

fun Components.TextAreaScene() = this.registerFunctionalComponent({}) {
    Page {
        head("Components / TextArea")
        body {
            DocPage {
                title("TextArea")
                p {
                    div {
                        style.width = "400px"

                        TextArea("Empty", FieldModel("")) {
                            infoText = "This is an infoText"
                        }

                        div { style.height = "32px" }

                        TextArea("With value", FieldModel("Some default value")) {
                            infoText = "This is an infoText"
                        }

                        div { style.height = "32px" }

                        TextArea("With error", FieldModel("Some default value", "This is an error message")) {
                            infoText = "This is an infoText"
                        }

                        div { style.height = "32px" }

                        TextArea("Disabled", FieldModel("Some default value")) {
                            infoText = "This is an infoText"
                            disabled = true
                        }
                    }
                }
            }
        }
    }
}