package visage.ds.components

import visage.core.*
import visage.dom.div
import visage.ds.colorpalette.Skin

class MHorizontalLoadIndicator(val progress: Int?) : AComponent<MHorizontalLoadIndicator.Companion.State>() {

    companion object {
        class State(var progress: Int = 0) {
            var animation: Animation? = null
        }
    }

    var height: Int = 8
    var color: String = Skin.palette.primaryColor

    override fun initState(): State {
        if (progress == null) {
            return State(0)
        } else {
            return State(progress)
        }
    }

    override fun onComponentDidMount() {
        if (progress == null) {
            this.state.animation = Animation(0, 100, 1000, this.state::progress, 0, EAnimationLoopType.PING_PONG)
            this.state.animation!!.start()
        }
    }

    override fun onComponentWillUnmount() {
        if (this.state.animation != null) {
            this.state.animation!!.stop()
        }
    }

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            style.apply {
                width = "100%"
                height = "${this@MHorizontalLoadIndicator.height}px"
                display = "flex"
                backgroundColor = Skin.palette.subHeaderBgColor
            }

            div {
                style.apply {
                    width = "${this@MHorizontalLoadIndicator.state.progress}%"
                    height = "100%"
                    backgroundColor = this@MHorizontalLoadIndicator.color
                }
            }
        }
    }

}

fun Components.HorizontalLoadIndicator(progress: Int?, init: (MHorizontalLoadIndicator.() -> Unit) = {  }) =
    this.registerComponent(MHorizontalLoadIndicator(progress), init)