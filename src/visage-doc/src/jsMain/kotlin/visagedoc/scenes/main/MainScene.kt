package visagedoc.scenes.main

import visage.core.Components
import visage.core.Navigation
import visage.core.Router
import visage.core.route
import visage.ds.components.page.*
import visagedoc.scenes.HomeScene
import visagedoc.scenes.ds.ShowcaseScene
import visagedoc.scenes.ds.components.*

fun Components.MainScene() = this.registerFunctionalComponent({}) {
    Application {
        menu {
            items {
                menuItem("Home", "home", "/index.vsg")

//                group("Basics")
//                menuItem("Getting started", "flight_takeoff", "/basics/gettingStarted.vsg")
//                menuItem("Type-safe builders", "precision_manufacturing", "/basics/typeSafeBuilders.vsg")
//                groupMenuItem("Elements", "code", "/basics/elements/") {
//                    menuItem("Rendering elements", "/basics/elements/renderingElements.vsg")
//                    menuItem("Using elements", "/basics/elements/usingElements.vsg")
//                    menuItem("Styling elements", "/basics/elements/stylingElements.vsg")
//                }
//
                group("Design System")
                menuItem("Showcase", "visibility", "/ds/showcase.vsg")
                groupMenuItem("Components", "dashboard", "/ds/components/") {
                    menuItem("Button", "/ds/components/button.vsg")
                    menuItem("CheckBox", "/ds/components/checkBox.vsg")
                    menuItem("Select", "/ds/components/select.vsg")
//                    menuItem("Table", "/ds/components/table.vsg")
                    menuItem("TextArea", "/ds/components/textArea.vsg")
                    menuItem("TextField", "/ds/components/textField.vsg")
                }
                //menuItem("Skin", "color_lens", "/ds/skin.vsg")
            }
        }

        Router {

            route("/index.vsg") {
                HomeScene()
            }
            route("/ds/showcase.vsg") {
                ShowcaseScene()
            }

            // basics
//            route("/basics/gettingStarted.vsg") {
//                GettingStartedScene()
//            }
//            route("/basics/typeSafeBuilders.vsg") {
//                TypeSafeBuildersScene()
//            }
//            route("/basics/elements/renderingElements.vsg") {
//                RenderingElementsScene()
//            }
//            route("/basics/elements/usingElements.vsg") {
//                UsingElementsScene()
//            }
//            route("/basics/elements/stylingElements.vsg") {
//                StylingElementsScene()
//            }

            // design system
            route("/ds/components/button.vsg") {
                ButtonScene()
            }
            route("/ds/components/checkBox.vsg") {
                CheckBoxScene()
            }
            route("/ds/components/select.vsg") {
                SelectScene()
            }
//            route("/ds/components/table.vsg") {
//                TableScene()
//            }
            route("/ds/components/textArea.vsg") {
                TextAreaScene()
            }
            route("/ds/components/textField.vsg") {
                TextFieldScene()
            }

            route("*") {
                Navigation.pushLocation("/ds/showcase.vsg")
            }
        }
    }
}