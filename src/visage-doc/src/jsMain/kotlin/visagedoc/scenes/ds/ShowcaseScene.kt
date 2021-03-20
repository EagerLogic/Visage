package visagedoc.scenes.ds

import visage.core.AComponent
import visage.core.Components
import visage.core.Visage
import visage.ds.colorpalette.DefaultDarkPalette
import visage.ds.colorpalette.DefaultLightPalette
import visage.ds.colorpalette.Skin
import visage.ds.components.*
import visage.ds.components.page.Page
import visage.ds.forms.FieldModel
import visage.ds.forms.MetaFieldModel
import visage.ds.forms.SelectOption

fun Components.ShowcaseScene() = this.registerComponent(CShowcaseScene(), {})

class CShowcaseScene() : AComponent<CShowcaseScene.Companion.State>() {

    companion object {
        class State() {
            var isDialogOpen: Boolean = false
            val dialogCbModel = FieldModel(false)
        }
    }

    override fun initState(): State {
        return State()
    }

    override fun Components.render(children: List<AComponent<*>>) {
        Page() {
            head("Showcase") {
                IconButton("group", EButtonColor.Secondary) {}
                ResponsiveButtonWithIcon("refresh", "Change skin", EButtonColor.Primary) {
                    if (Skin.palette is DefaultLightPalette) {
                        Skin.palette = DefaultDarkPalette()
                    } else {
                        Skin.palette = DefaultLightPalette()
                    }
                    Visage.rerender()
                }
            }

            tab("General") {
                ResponsiveVBox {
                    Card {  }
                    Card {  }
                    Card {  }
                    Card {  }
                }
                Card {
                    infoText = "This is an info text which can be attached to every card to describe what it is good for."
                    messages {
                        info("This is an info message")
                        success("This is a success message")
                        warning("This is a warning message")
                        danger("This is a danger message")
                    }
                    header("Example card") {
                        IconButton("group") {}
                        ResponsiveButtonWithIcon("add", "Show modal", EButtonColor.Success) {
                            this@CShowcaseScene.state.isDialogOpen = true
                            this@CShowcaseScene.refresh()
                        }
                    }
                    body {
                        Table() {
                            columns {
                                fixedColumn("Name")
                                fixedColumn("Email")
                                responsiveColumn("Yearly")
                                responsiveColumn("Rolled")
                                responsiveColumn("Used up")
                                responsiveColumn("Remaining")

                            }
                            body {
                                row {
                                    cell("Kovacs Marton")
                                    cell("marton.kovacs@gmail.com")
                                    cell("25")
                                    cell("0")
                                    cell("12")
                                    cell("13")
                                }
                                row {
                                    cell("Kiss Istvan")
                                    cell("kissistvan@gmail.com")
                                    cell("25")
                                    cell("0")
                                    cell("12")
                                    cell("13")
                                }
                                row {
                                    cell("Dobrogi Gabor")
                                    cell("dobrogig@gmail.com")
                                    cell("25")
                                    cell("0")
                                    cell("12")
                                    cell("13")
                                }
                                row {
                                    cell("Oriza Triznyak")
                                    cell("trizi@gmail.com")
                                    cell("25")
                                    cell("0")
                                    cell("12")
                                    cell("13")
                                }
                            }
                        }
                    }
                    footer {
                        text = "Last updated 12 minutes ago"
                        IconButton("refresh") {}
                        IconButton("delete", EButtonColor.Danger) {}
                    }
                }

                Card {
                    header("Example history list")
                    body {
                        HistoryList {
                            itemWithIcon("home", "First item", "2021-01-15", "This is an exxample item with some fake text")
                            itemWithIcon("home", "First item", "2021-01-15", "This is an exxample item with some fake text",  Skin.palette.successColor)
                            itemWithIcon("home", "First item", "2021-01-15", "This is an exxample item with some fake text",  Skin.palette.warningColor)
                            itemWithIcon("home", "First item", "2021-01-15", "This is an exxample item with some fake text",  Skin.palette.dangerColor)
                        }
                    }
                }

                if (this@CShowcaseScene.state.isDialogOpen) {
                    Modal("Example dialog") {
                        infoText = "This is an infotext"
                        error = "This is an error message"
                        TextField("Email", FieldModel("")) {
                            infoText = "Your email address"
                        }
                        Select(
                            "Gender",
                            MetaFieldModel<String, List<SelectOption>>(
                                "male",
                                listOf(SelectOption("male", "Male"), SelectOption("female", "Female"))
                            )
                        ) {
                            infoText = "Select your gender"
                        }
                        TextArea("Introduction", FieldModel("")) {
                            infoText = "Introduce yourself in a few words"
                        }
                        CheckBox("I have read and understand everything", this@CShowcaseScene.state.dialogCbModel)

                        footer {
                            Button("Cancel", EButtonColor.Secondary, EButtonVariant.Link) {
                                this@CShowcaseScene.state.isDialogOpen = false
                                this@CShowcaseScene.refresh()
                            }
                            Button("Save", EButtonColor.Primary) {
                                this@CShowcaseScene.state.isDialogOpen = false
                                this@CShowcaseScene.refresh()
                            }
                        }
                    }
                }
            }

            tab("Users") {
                +"Tab 2 content"
            }

            tab("Teams") {
                +"Tab 3 content"
            }
        }


    }

}