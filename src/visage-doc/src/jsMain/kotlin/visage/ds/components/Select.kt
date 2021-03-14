//package visage.ds.components
//
//import org.w3c.dom.HTMLSelectElement
//import visage.core.Components
//import visage.dom.Css
//import visage.dom.option
//import visage.dom.select
//import visage.ds.forms.MetaFieldModel
//import visage.ds.forms.SelectOption
//
//fun Components.Select(label: String, model: MetaFieldModel<String, List<SelectOption>>, init: CSelect.() -> Unit = { }) =
//    this.registerComponent(CSelect(label, model), init)
//
//class CSelect(label: String, model: MetaFieldModel<String, List<SelectOption>>) : AInputBase<String, List<SelectOption>>(label, model) {
//
//    companion object {
//        init {
//            Css.createBlock("select:focus") {
//                this["outline"] = "none"
//            }
//        }
//    }
//
//
//    override fun Components.renderInput() {
//        select {
//            id = this@CSelect.inputId
//            attr["value"] = this@CSelect.model.value
//            if (this@CSelect.disabled) {
//                attr["disabled"] = "true"
//            }
//            classes = inputStyle
//
//            events["focus"] = {
//                this@CSelect.handleFocusChanged()
//            }
//            events["blur"] = {
//                this@CSelect.handleFocusChanged()
//            }
//            events["input"] = {
//                val evt = it
//                this@CSelect.handleValueChange((evt.target as HTMLSelectElement).value)
//            }
//
//            (this@CSelect.model as MetaFieldModel<String, List<SelectOption>>).meta.forEach {
//                option(it.label, it.value, this@CSelect.model.value == it.value)
//            }
//        }
//    }
//
//}