//package visagedoc.scenes.ds.components
//
//import visage.core.Components
//import visage.dom.div
//import visage.ds.components.PageContent
//import visage.ds.components.Select
//import visage.ds.components.section
//import visage.ds.forms.MetaFieldModel
//import visage.ds.forms.SelectOption
//import visagedoc.components.DocPage
//import visagedoc.components.codeBlock
//import visagedoc.components.p
//import visagedoc.components.title
//
//fun Components.SelectScene() = this.registerFunctionalComponent({}) {
//    PageContent("Components / Select") {
//        section("This is a section") {
//            DocPage {
//                title("Select")
//                p {
//                    +"""
//                    The select component is useful when we want the user to select from a predefined set of options.
//                """
//                }
//                p {
//                    div {
//                        style.width = "400px"
//
//                        var model = MetaFieldModel(
//                            "", listOf(
//                                SelectOption("", ""),
//                                SelectOption("o1", "Option 1"),
//                                SelectOption("o2", "Option 2"),
//                                SelectOption("o3", "Option 3"),
//                            )
//                        )
//
//                        Select("Empty", model) {
//                            infoText = "This is an infoText"
//                        }
//
//                        div { style.height = "32px" }
//
//                        model = MetaFieldModel(
//                            "o2", listOf(
//                                SelectOption("o1", "Option 1"),
//                                SelectOption("o2", "Option 2"),
//                                SelectOption("o3", "Option 3"),
//                            )
//                        )
//
//                        Select("With value", model) {
//                            infoText = "This is an infoText"
//                        }
//
//                        div { style.height = "32px" }
//
//                        model = MetaFieldModel(
//                            "o2", listOf(
//                                SelectOption("o1", "Option 1"),
//                                SelectOption("o2", "Option 2"),
//                                SelectOption("o3", "Option 3"),
//                            ), "This is an error message"
//                        )
//
//                        Select("With error", model) {
//                            infoText = "This is an infoText"
//                        }
//
//                        div { style.height = "32px" }
//
//                        model = MetaFieldModel(
//                            "o2", listOf(
//                                SelectOption("o1", "Option 1"),
//                                SelectOption("o2", "Option 2"),
//                                SelectOption("o3", "Option 3"),
//                            )
//                        )
//
//                        Select("Disabled", model) {
//                            infoText = "This is an infoText"
//                            disabled = true
//                        }
//
//                    }
//
//                }
//
//                codeBlock(
//                    """
//var model = MetaFieldModel("", listOf(
//    SelectOption("", ""),
//    SelectOption("o1", "Option 1"),
//    SelectOption("o2", "Option 2"),
//    SelectOption("o3", "Option 3"),
//))
//
//Select("Empty", model) {
//    infoText = "This is an infoText"
//}
//
//div {style.height = "32px"}
//
//model = MetaFieldModel("o2", listOf(
//    SelectOption("o1", "Option 1"),
//    SelectOption("o2", "Option 2"),
//    SelectOption("o3", "Option 3"),
//))
//
//Select("With value", model) {
//    infoText = "This is an infoText"
//}
//
//div {style.height = "32px"}
//
//model = MetaFieldModel("o2", listOf(
//    SelectOption("o1", "Option 1"),
//    SelectOption("o2", "Option 2"),
//    SelectOption("o3", "Option 3"),
//), "This is an error message")
//
//Select("With error", model) {
//    infoText = "This is an infoText"
//}
//
//div {style.height = "32px"}
//
//model = MetaFieldModel("o2", listOf(
//    SelectOption("o1", "Option 1"),
//    SelectOption("o2", "Option 2"),
//    SelectOption("o3", "Option 3"),
//))
//
//Select("Disabled", model) {
//    infoText = "This is an infoText"
//    disabled = true
//}
//            """
//                )
//            }
//        }
//    }
//}