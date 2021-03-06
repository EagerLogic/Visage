package visagedoc.scenes.main

import visage.core.*
import visage.ds.components.*
import visagedoc.scenes.HomeScene
import visagedoc.scenes.ds.basics.DomComponentsScene
import visagedoc.scenes.ds.basics.GettingStartedScene
import visagedoc.scenes.ds.components.ButtonScene
import visagedoc.scenes.ds.components.TextFieldScene

class CMainScene() : AScene<Unit?, MainSceneModel, MainSceneState, MainController>(null) {

    override fun createController(): MainController {
        return MainController(this)
    }

    override fun initState(): MainSceneState {
        return MainSceneState()
    }

    override fun Components.render(children: List<AComponent<*>>) {
        BasePageLayout {
            menu {
                items {
                    menuItem("Home", "home", "/index.vsg")

                    group("Basics")
                    menuItem("Getting started", "flight_takeoff", "/basics/gettingStarted.vsg")
                    menuItem("DOM Components", "code", "/basics/domComponents.vsg")

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
                route("/basics/domComponents.vsg") {
                    DomComponentsScene()
                }

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
}

class MainSceneState {

}

fun Components.MainScene() = this.registerComponent(CMainScene(), {})