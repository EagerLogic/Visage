package visagedoc.scenes.main

import visage.core.Components
import visage.core.Navigation
import visage.core.Router
import visage.core.route
import visage.ds.components.*
import visagedoc.scenes.HomeScene
import visagedoc.scenes.ds.basics.GettingStartedScene
import visagedoc.scenes.ds.basics.TypeSafeBuildersScene
import visagedoc.scenes.ds.basics.elements.RenderingElementsScene
import visagedoc.scenes.ds.basics.elements.StylingElementsScene
import visagedoc.scenes.ds.basics.elements.UsingElementsScene
import visagedoc.scenes.ds.components.ButtonScene
import visagedoc.scenes.ds.components.TextFieldScene

fun Components.MainScene() = this.registerFunctionalComponent({}) {
    BasePageLayout {
        menu {
            items {
                menuItem("Home", "home", "/index.vsg")

                group("Basics")
                menuItem("Getting started", "flight_takeoff", "/basics/gettingStarted.vsg")
                menuItem("Type-safe builders", "precision_manufacturing", "/basics/typeSafeBuilders.vsg")
                groupMenuItem("Elements", "code", "/basics/elements/") {
                    menuItem("Rendering elements", "/basics/elements/renderingElements.vsg")
                    menuItem("Using elements", "/basics/elements/usingElements.vsg")
                    menuItem("Styling elements", "/basics/elements/stylingElements.vsg")
                }

                group("Design System")
                groupMenuItem("Components", "dashboard", "/ds/components/") {
                    menuItem("Button", "/ds/components/button.vsg")
                    menuItem("TextField", "/ds/components/textField.vsg")
                }
                //menuItem("Skin", "color_lens", "/ds/skin.vsg")
            }
        }

        Router {
            route("/index.vsg") {
                HomeScene()
            }

            // basics
            route("/basics/gettingStarted.vsg") {
                GettingStartedScene()
            }
            route("/basics/typeSafeBuilders.vsg") {
                TypeSafeBuildersScene()
            }
            route("/basics/elements/renderingElements.vsg") {
                RenderingElementsScene()
            }
            route("/basics/elements/usingElements.vsg") {
                UsingElementsScene()
            }
            route("/basics/elements/stylingElements.vsg") {
                StylingElementsScene()
            }

            // design system
            route("/ds/components/button.vsg") {
                ButtonScene()
            }
            route("/ds/components/textField.vsg") {
                TextFieldScene()
            }
            route("*") {
                Navigation.pushLocation("/index.vsg")
            }
        }
    }
}