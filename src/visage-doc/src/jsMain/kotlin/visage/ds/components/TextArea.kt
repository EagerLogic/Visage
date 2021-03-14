//package visage.ds.components
//
//import kotlinx.browser.window
//import org.w3c.dom.HTMLTextAreaElement
//import visage.core.Components
//import visage.dom.Css
//import visage.dom.textarea
//import visage.ds.forms.FieldModel
//
//fun Components.TextArea(label: String, model: FieldModel<String>, init: (CTextArea.() -> Unit) = {}) =
//    this.registerComponent(CTextArea(label, model), init)
//
//class CTextArea(label: String, model: FieldModel<String>) : AInputBase<String, Unit>(label, model) {
//
//    companion object {
//        init {
//            Css.createBlock("textarea:focus") {
//                this["outline"] = "none"
//            }
//        }
//    }
//
//    override fun onComponentDidMount() {
//        val e = window.document.getElementById(this.inputId) as HTMLTextAreaElement
//        e.value = model.value
//    }
//
//    override fun Components.renderInput() {
//        textarea {
//            classes = inputStyle
//
//            id = this@CTextArea.inputId
//            if (this@CTextArea.disabled) {
//                attr["disabled"] = "true"
//            }
//
//            events["focus"] = {
//                this@CTextArea.handleFocusChanged()
//            }
//            events["blur"] = {
//                this@CTextArea.handleFocusChanged()
//            }
//            events["input"] = {
//                this@CTextArea.handleValueChange((it.target as HTMLTextAreaElement).value)
//            }
//        }
//    }
//}