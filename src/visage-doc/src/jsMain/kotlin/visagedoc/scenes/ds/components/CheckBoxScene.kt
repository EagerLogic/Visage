package visagedoc.scenes.ds.components

import visage.core.Components
import visage.dom.div
import visage.ds.components.CheckBox
import visage.ds.components.page.Page
import visage.ds.forms.FieldModel
import visagedoc.components.DocPage
import visagedoc.components.codeBlock
import visagedoc.components.p
import visagedoc.components.title

private val uncheckedModel = FieldModel(false)
private val longTextModel = FieldModel(false)
private val disabledModel = FieldModel(false)
private val checkedModel = FieldModel(true)
private val errorModel = FieldModel(false, "This is an error message")

fun Components.CheckBoxScene() = this.registerFunctionalComponent({}) {
    Page {
        head("Components / CheckBox")
        body {
            DocPage {
                title("CheckBox")
                p {
                    div {
                        style.apply {
                            width = "400px"
                            maxWidth = "100%"
                        }

                        CheckBox("Unchecked", uncheckedModel)

                        div { style.height = "16px" }

                        CheckBox("Checked", checkedModel)

                        div { style.height = "16px" }

                        CheckBox("With error", errorModel)

                        div { style.height = "16px" }

                        CheckBox("Lorem ipsum dolor sit amet unesence elite une ante di grande tu teledi ente.", longTextModel)

                        div { style.height = "16px" }

                        CheckBox("Disabled", disabledModel) {
                            disabled = true
                        }
                    }
                }
                codeBlock("""
val uncheckedModel = FieldModel(false)
val longTextModel = FieldModel(false)
val disabledModel = FieldModel(false)
val checkedModel = FieldModel(true)
val errorModel = FieldModel(false, "This is an error message")

CheckBox("Unchecked", uncheckedModel)

div { style.height = "16px" }

CheckBox("Checked", checkedModel)

div { style.height = "16px" }

CheckBox("With error", errorModel)

div { style.height = "16px" }

CheckBox("Lorem ipsum dolor sit amet unesence elite une ante di grande tu teledi ente.", longTextModel)

div { style.height = "16px" }

CheckBox("Disabled", disabledModel) {
    disabled = true
} 
                """)

            }
        }
    }
}