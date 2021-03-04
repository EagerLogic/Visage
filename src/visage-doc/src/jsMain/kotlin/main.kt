import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import visage.core.Navigation
import visage.core.Visage
import visagedoc.scenes.main.MainScene

fun main() {
    console.log("Visage doc site loading...")
    window.onload = {
        start()
    }
}

fun start() {
    console.log("Visage doc site starting...")
    Visage.init(window.document.getElementById("root") as HTMLElement) {
        MainScene()
    }
    Navigation.onLocationChanged.addListener {
        Visage.rerender()
    }
    Visage.rerender()
}