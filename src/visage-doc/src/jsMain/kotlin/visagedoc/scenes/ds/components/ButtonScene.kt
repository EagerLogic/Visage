package visagedoc.scenes.ds.components

import kotlinx.browser.window
import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.div
import visage.ds.components.EButtonColor
import visage.ds.components.EButtonSize
import visage.ds.components.EButtonVariant
import visage.ds.components.ResponsiveButton
import visage.ds.components.page.Page
import visagedoc.components.*

class CButtonScene : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        Page {
            head("Components / Button")
            body {
                DocPage {
                    title("Introduction")
                    p {
                        +"""
                    The button component represents a simple pushable button, and you can handle the onClick event of it.
                    I don't think it requires too much introduction, (I hope) everybody know what a button is.
                    """

                    }

                    title("Color")
                    p {
                        +"You can set the color of the button by it's color property."
                    }

                    p {
                        ResponsiveButton("Primary") {
                            color = EButtonColor.Primary
                        }
                        div { style.width = "16px"; style.display = "inline-block" }
                        ResponsiveButton("Secondary") {
                            color = EButtonColor.Secondary
                        }
                        div { style.width = "16px"; style.display = "inline-block" }
                        ResponsiveButton("Success") {
                            color = EButtonColor.Success
                        }
                        div { style.width = "16px"; style.display = "inline-block" }
                        ResponsiveButton("Warning") {
                            color = EButtonColor.Warning
                        }
                        div { style.width = "16px"; style.display = "inline-block" }
                        ResponsiveButton("Danger") {
                            color = EButtonColor.Danger
                        }
                    }

                    codeBlock(
                        """
Button("Primary") {
    color = EButtonColor.Primary
}
div { style.width = "16px"; style.display = "inline-block" }
Button("Secondary") {
    color = EButtonColor.Secondary
}
div { style.width = "16px"; style.display = "inline-block" }
Button("Success") {
    color = EButtonColor.Success
}
div { style.width = "16px"; style.display = "inline-block" }
Button("Warning") {
    color = EButtonColor.Warning
}
div { style.width = "16px"; style.display = "inline-block" }
Button("Danger") {
    color = EButtonColor.Danger
}
                """
                    )




                    title("Size")
                    p {
                        +"You can choose from 3 different sizes. The default is Normal."
                    }
                    p {
                        ResponsiveButton("Small") {
                            size = EButtonSize.Small
                        }
                        div { style.width = "16px"; style.display = "inline-block" }
                        ResponsiveButton("Normal") {
                            size = EButtonSize.Normal
                        }
                        div { style.width = "16px"; style.display = "inline-block" }
                        ResponsiveButton("Large") {
                            size = EButtonSize.Large
                        }
                    }

                    codeBlock(
                        """
Button("Small") {
    size = EButtonSize.Small
}
Button("Normal") {
    size = EButtonSize.Normal
}
Button("Large") {
    size = EButtonSize.Large
}
                """
                    )




                    title("Variant")
                    p {
                        +"You can also choose from 3 different variants. The default is Filled."
                    }
                    p {
                        ResponsiveButton("Filled") {
                            variant = EButtonVariant.Filled
                        }
                        div { style.width = "16px"; style.display = "inline-block" }
                        ResponsiveButton("Outlined") {
                            variant = EButtonVariant.Outlined
                        }
                        div { style.width = "16px"; style.display = "inline-block" }
                        ResponsiveButton("Link") {
                            variant = EButtonVariant.Link
                        }
                    }
                    codeBlock(
                        """
Button("Filled") {
    variant = EButtonVariant.Filled
}
Button("Outlined") {
    variant = EButtonVariant.Outlined
}
Button("Link") {
    variant = EButtonVariant.Link
}
                """
                    )




                    title("OnClick event")
                    p {
                        +"You can listen to click events using the onClick attribute."
                    }
                    p {
                        ResponsiveButton("Click me!") {
                            onClick = {
                                window.alert("Thanks!")
                            }
                        }
                    }
                    codeBlock(
                        """
Button("Click me!") {
    onClick = {
        window.alert("Thanks!")
    }
}
                """
                    )




                    properties {
                        prop("text", "The title of the button.")
                        prop("color", "The color of the button.", "Default")
                        prop("size", "The size of the button.", "Normal")
                        prop("variant", "The variant of the button.", "Filled")
                        prop(
                            "disabled",
                            "If the button is disabled than it isn't clickable, and onClick event doesn't fired.",
                            "false"
                        )
                        prop("onClick", "The event which is fired when the button is clicked.")
                    }
                }
            }
        }
    }
}

fun Components.ButtonScene() = this.registerComponent(CButtonScene(), {})