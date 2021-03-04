package visagedoc.scenes.main

import visage.core.AController

class MainController(scene: CMainScene) : AController<Unit?, MainSceneModel, MainSceneState>(null, scene) {
    override fun createInitialModel(): MainSceneModel {
        return MainSceneModel()
    }

    override fun init() {

    }
}