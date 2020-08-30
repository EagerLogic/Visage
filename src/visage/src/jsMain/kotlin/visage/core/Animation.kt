package visage.core

import kotlin.browser.window
import kotlin.js.Date
import kotlin.reflect.KMutableProperty0

enum class EAnimationLoopType {
    NONE, RESTART, PING_PONG
}

/**
 * Animates a component
 * @param startValue
 * @param endValue
 * @param duration in milliseconds (1000ms = 1sec)
 * @param binding pass a class property that updates when animation steps to next update cycle. Property must to have a public setter
 * @param repeat "0" means infinite repeats. If loopType set NONE repeat will be ignored
 * @param loopType NONE will stop the animation once it finished. RESTART and PING_PONG will repeat based on the repeat property. PING_PONG plays the animation back and forth
 * @see [migo-animation](https://migo-showcase.r3-test.minicrm.eu/migo/animation)
 */
class Animation(
    val startValue: Int,
    val endValue: Int,
    val duration: Int,
    val binding: KMutableProperty0<Int>? = null,
    val repeat: Int = 0,
    val loopType: EAnimationLoopType = EAnimationLoopType.NONE,
    init: (Animation.() -> Unit)? = null
) {

    /**
     * Refresh only the given component
     */
    var updateComponent: AComponent<*>? = null

    var disableAutoRerender: Boolean = false

    /**
     * Event that triggers when animation updated (eg: steps to the next update cycle)
     * Subscribed client will get the actual value in the current animation cycle
     */
    var onTick: ((value: Int) -> Unit)? = null

    /**
     * Event that triggers when animation finished (or stopped manually)
     */
    var onStopped: (() -> Unit)? = null

    /**
     * Indicates whether the animation started already
     */
    val isStarted: Boolean
        get() {
            return this.startTime != null
        }

    /**
     * Actual value in the current animation cycle
     */
    var currentValue: Int = this.startValue
        private set

    private var startTime: Long? = null

    init {
        init?.invoke(this)
    }

    /**
     * Orders the animator to start the animation
     */
    fun start() {
        if (isStarted) {
            return
        }

        this.currentValue = this.startValue
        this.startTime = Date.now().toLong()
        Animator.addAnimation(this)
        this.notifyNewValue()
    }

    /**
     * Orders the animator to (manually) stop the animation
     * Trigger the onStopped event as well
     */
    fun stop() {
        if (!isStarted) {
            return
        }

        this.startTime = null
        Animator.removeAnimation(this)

        if (this.onStopped != null) {
            this.onStopped!!()
            if (this.updateComponent != null) {
                this.updateComponent!!.refresh()
            }
        }
    }

    internal fun tick() {
        val timeDelta = (Date.now().toLong() - this.startTime!!).toLong()

        if (isAnimationEnded(timeDelta)) {
            this.currentValue = this.calculateEndValue()

            this.notifyNewValue()
            this.stop()
            return
        }


        this.currentValue = this.calculateCurrentValue(timeDelta)
        this.notifyNewValue()
    }

    private fun isAnimationEnded(timeDelta: Long): Boolean {
        return (this.loopType == EAnimationLoopType.NONE && timeDelta >= this.duration)
                || (this.repeat > 0 && timeDelta >= this.repeat * this.duration)
    }

    private fun calculateEndValue(): Int {
        return when (this.loopType) {
            EAnimationLoopType.NONE -> {
                this.endValue
            }
            EAnimationLoopType.RESTART -> {
                this.endValue
            }
            EAnimationLoopType.PING_PONG -> {
                if (this.repeat % 2 == 0) {
                    this.startValue
                } else {
                    this.endValue
                }
            }
        }
    }

    private fun calculateCurrentValue(timeDelta: Long): Int {
        val iterationTime = timeDelta % this.duration
        var pos = iterationTime / this.duration.toDouble()
        if (this.loopType == EAnimationLoopType.PING_PONG) {
            if ((timeDelta / this.duration) % 2 != 0L) {
                pos = 1.0 - pos
            }
        }

        return (this.startValue + ((this.endValue - this.startValue) * pos)).toInt()
    }

    private fun notifyNewValue() {
        if (this.binding != null) {
            this.binding.set(this.currentValue)
        }
        if (this.onTick != null) {
            this.onTick!!(this.currentValue)
        }
    }

}

class Animator private constructor() {

    companion object {

        private val runningAnimations = mutableListOf<Animation>()
        private var intervalId: Int? = null

        fun addAnimation(animation: Animation) {
            if (this.isAnimationAdded(animation)) {
                throw Exception("This animation is already added!")
            }

            runningAnimations.add(animation)

            if (intervalId == null) {
                intervalId = window.setInterval(this::onTick, 10)
            }
        }

        fun removeAnimation(animation: Animation) {
            if (!isAnimationAdded(animation)) {
                throw Exception("This animation isn't added!")
            }

            this.runningAnimations.remove(animation)

            if (this.runningAnimations.size < 1) {
                if (this.intervalId != null) {
                    window.clearInterval(this.intervalId!!)
                    intervalId = null
                }
            }
        }

        private fun isAnimationAdded(animation: Animation): Boolean {
            return this.runningAnimations.indexOf(animation) > -1
        }

        private fun onTick() {
            var rerender = false
            for (animation in this.runningAnimations) {
                try {
                    animation.tick()
                } catch (ex: Exception) {
                    console.error(ex)
                }

                if (animation.updateComponent != null) {
                    animation.updateComponent!!.refresh()
                } else if (!animation.disableAutoRerender) {
                    rerender = true
                }
            }

            if (rerender) {
                Visage.rerender()
            }
        }
    }

}