package visagedoc.scenes.main

import visage.core.*
import visage.dom.text
import visage.ds.components.*
import visagedoc.scenes.HomeScene
import visagedoc.scenes.components.buttonscene.ButtonScene

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

//                    group("Basics")
//                    menuItem("Quick start", "home", "/install.vsg")
//                    menuItem("Components", "home", "/install.vsg")
//                    menuItem("Composites", "home", "/install.vsg")
//
                    group("Design System")
                    groupMenuItem("Components", "dashboard", "/ds/components/") {
                        menuItem("Button", "/ds/components/button.vsg")
//                        menuItem("Icon", "/components/icon.vsg")
//                        menuItem("Modal", "/components/modal.vsg")
//                        menuItem("Table", "/components/table.vsg")
//                        menuItem("Text", "/components/text.vsg")
//                        menuItem("TextField", "/components/textField.vsg")
//                        menuItem("VScrollBox", "/components/vScrollBox.vsg")
                    }
                }
            }

            Router {
                route("/index.vsg") {
                    HomeScene()
                }
                route("/ds/components/button.vsg") {
                    ButtonScene()
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