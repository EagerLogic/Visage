package visage.core

import kotlin.browser.window

internal val controllerCache = mutableMapOf<String, AController<*, *, *>>()

abstract class AScene<GProps, GModel, GState : Any, GController : AController<GProps, GModel, GState>>(val props: GProps) :
    AComponent<GState>() {

    val model: GModel
        get() {
            return this.controller.model
        }

    @Suppress("UNCHECKED_CAST")
    val controller: GController
        get() {
            val key = this::class.simpleName + ":" + this.props.toString()
            var controller: GController? = controllerCache[key].unsafeCast<GController?>()
            if (controller == null) {
                controller = createController()
                controllerCache[key] = controller
            }
            if (controller.scene != this) {
                controller.scene = this
            }
            return controller
        }

    override fun onComponentWillUnmount() {
        val key = this::class.simpleName + ":" + this.props.toString()
        controllerCache.remove(key)
    }

    protected abstract fun createController(): GController

}

abstract class AController<GProps, GModel, GState : Any>(
    val props: GProps,
    scene: AScene<GProps, GModel, GState, out AController<GProps, GModel, GState>>
) {

    public var scene: AScene<GProps, GModel, GState, out AController<GProps, GModel, GState>> = scene
    public get
    internal set

    private var _model: GModel? = null
    val model: GModel
        get() {
            if (this._model === null) {
                this._model = createInitialModel()
                window.setTimeout({
                    this.init()
                }, 0)
            }
            return this._model!!
        }

    var state: GState
        get() {
            return this.scene.state
        }
        set(value) {
            this.scene.state = value
            this.scene.refresh()
        }

    protected abstract fun init()
    protected abstract fun createInitialModel(): GModel

    protected fun render(newModel: GModel? = null) {
        if (newModel != null) {
            this._model = newModel
        }

        this.scene.refresh()
    }
}
