//package visagedoc.scenes.ds.components
//
//import kotlinx.browser.window
//import visage.core.AComponent
//import visage.core.APureComponent
//import visage.core.Components
//import visage.dom.div
//import visage.ds.components.*
//import visagedoc.components.*
//
//class CButtonScene : APureComponent() {
//    override fun Components.render(children: List<AComponent<*>>) {
//        PageContent("Components / Button") {
//            DocPage {
//                title("Introduction")
//                p {
//                    +"""
//                    The button component represents a simple pushable button, and you can handle the onClick event of it.
//                    I don't think it requires too much introduction, (I hope) everybody know what a button is.
//                    """
//
//                }
//
//                title("Color")
//                p {
//                    +"You can set the color of the button by it's color property."
//                }
//
//                p {
//                    Button("Default") {
//                        color = EButtonColor.Default
//                    }
//                    div { style.width = "16px"; style.display = "inline-block" }
//                    Button("Success") {
//                        color = EButtonColor.Success
//                    }
//                    div { style.width = "16px"; style.display = "inline-block" }
//                    Button("Warning") {
//                        color = EButtonColor.Warning
//                    }
//                    div { style.width = "16px"; style.display = "inline-block" }
//                    Button("Danger") {
//                        color = EButtonColor.Danger
//                    }
//                }
//
//                codeBlock("""
//Button("Default") {
//    color = EButtonColor.Default
//}
//Button("Success") {
//    color = EButtonColor.Success
//}
//Button("Warning") {
//    color = EButtonColor.Warning
//}
//Button("Danger") {
//    color = EButtonColor.Danger
//}
//                """)
//
//
//
//
//                title("Size")
//                p {
//                    +"You can choose from 3 different sizes. The default is Normal."
//                }
//                p {
//                    Button("Small") {
//                        size = EButtonSize.Small
//                    }
//                    div { style.width = "16px"; style.display = "inline-block" }
//                    Button("Normal") {
//                        size = EButtonSize.Normal
//                    }
//                    div { style.width = "16px"; style.display = "inline-block" }
//                    Button("Large") {
//                        size = EButtonSize.Large
//                    }
//                }
//
//                codeBlock("""
//Button("Small") {
//    size = EButtonSize.Small
//}
//Button("Normal") {
//    size = EButtonSize.Normal
//}
//Button("Large") {
//    size = EButtonSize.Large
//}
//                """)
//
//
//
//
//                title("Variant")
//                p {
//                    +"You can also choose from 3 different variants. The default is Filled."
//                }
//                p {
//                    Button("Filled") {
//                        variant = EButtonVariant.Filled
//                    }
//                    div { style.width = "16px"; style.display = "inline-block" }
//                    Button("Outlined") {
//                        variant = EButtonVariant.Outlined
//                    }
//                    div { style.width = "16px"; style.display = "inline-block" }
//                    Button("Link") {
//                        variant = EButtonVariant.Link
//                    }
//                }
//                codeBlock("""
//Button("Filled") {
//    variant = EButtonVariant.Filled
//}
//Button("Outlined") {
//    variant = EButtonVariant.Outlined
//}
//Button("Link") {
//    variant = EButtonVariant.Link
//}
//                """)
//
//
//
//
//                title("OnClick event")
//                p {
//                    +"You can listen to click events using the onClick attribute."
//                }
//                p {
//                    Button("Click me!") {
//                        onClick = {
//                            window.alert("Thanks!")
//                        }
//                    }
//                }
//                codeBlock("""
//Button("Click me!") {
//    onClick = {
//        window.alert("Thanks!")
//    }
//}
//                """)
//
//
//
//
//                properties {
//                    prop("text", "The title of the button.")
//                    prop("color", "The color of the button.", "Default")
//                    prop("size", "The size of the button.", "Normal")
//                    prop("variant", "The variant of the button.", "Filled")
//                    prop("disabled", "If the button is disabled than it isn't clickable, and onClick event doesn't fired.", "false")
//                    prop("onClick", "The event which is fired when the button is clicked.")
//                }
//            }
//        }
//    }
//}
//
//fun Components.ButtonScene() = this.registerComponent(CButtonScene(), {})