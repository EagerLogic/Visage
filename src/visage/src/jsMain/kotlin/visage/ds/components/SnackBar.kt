package visage.ds.components

import kotlinx.browser.window
import visage.core.AComponent
import visage.core.Components
import visage.core.Event
import visage.core.IdGenerator
import visage.dom.CssClass
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight
import kotlin.js.Date

object SnackBar {

    internal val onShow = Event<SnackBarItem>()

    fun showInfo(message: String) {
        this.onShow.fire(SnackBarItem(ESnackBarType.Info, message))
    }

    fun showSuccess(message: String) {
        this.onShow.fire(SnackBarItem(ESnackBarType.Success, message))
    }

    fun showWarning(message: String) {
        this.onShow.fire(SnackBarItem(ESnackBarType.Warning, message))
    }

    fun showDanger(message: String) {
        this.onShow.fire(SnackBarItem(ESnackBarType.Danger, message))
    }

}

class CSnackBar() : AComponent<CSnackBar.Companion.State>() {

    companion object {
        class State() {
            internal val items = mutableListOf<SnackBarItem>()
            var timerId: Int? = null
            internal var listener: ((item: SnackBarItem) -> Unit)? = null
        }
    }

    override fun initState(): State {
        return State()
    }

    override fun onComponentDidMount() {
        this.state.listener = this::handleItemAdded
        SnackBar.onShow.addListener(this.state.listener!!)
    }

    override fun onComponentWillUnmount() {
        SnackBar.onShow.removeListener(this.state.listener!!)
        this.stopTimerIfNeeded()
    }

    private fun handleItemAdded(item: SnackBarItem) {
        this.state.items.add(0, item)
        if (this.state.items.size == 1) {
            startTimerIfNeeded()
        }
        this.refresh()
    }

    private fun removeItem(id: Long) {
        val index = this.state.items.indexOfFirst {
            it.id == id
        }
        this.state.items.removeAt(index)
        if (this.state.items.size < 1) {
            this.stopTimerIfNeeded()
        }
        this.refresh()
    }

    private fun startTimerIfNeeded() {
        if (this.state.timerId == null) {
            this.state.timerId = window.setInterval(this::handleTimerTick, 100)
        }
    }

    private fun stopTimerIfNeeded() {
        if (this.state.timerId != null) {
            window.clearInterval(this.state.timerId!!)
            this.state.timerId = null
        }
    }

    private fun handleTimerTick() {
        val itemsToDelete = this.state.items.filter {
            it.closeAt <= Date.now()
        }
        itemsToDelete.forEach {
            this.removeItem(it.id)
        }
    }

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = rootContainerStyle

            this@CSnackBar.state.items.forEachIndexed { index, item ->
                if (index > 0) {
                    div {
                        style.apply {
                            height = "16px"
                            minHeight = height
                        }
                    }
                }
                div {
                    classes = itemStyle
                    style.backgroundColor = item.type.color

                    Text(
                        item.message,
                        TextStyle(
                            16,
                            EFontWeight.SemiBold,
                            Skin.palette.negativeNormalTextColor,
                            false
                        ),
                        true
                    )
                }
            }
        }
    }


}

enum class ESnackBarType(val color: String) {
    Info(Skin.palette.infoColor),
    Success(Skin.palette.successColor),
    Warning(Skin.palette.warningColor),
    Danger(Skin.palette.dangerColor),
}

internal class SnackBarItem(
    val type: ESnackBarType,
    val message: String
) {
    val id = IdGenerator.nextId
    var closeAt = Date.now() + 5000
}

private val rootContainerStyle by CssClass {
    position = "fixed"
    right = "0px"
    bottom = "0px"
    padding = "24px"
}

private val itemStyle by CssClass {
    width = "400px"
    maxWidth = width
    overflow = "hidden"
    padding = "16px"
    borderRadius = "8px"
    boxShadow = "1px 1px 5px rgba(0,0,0, 0.3)"
    zIndex = "20000"
}