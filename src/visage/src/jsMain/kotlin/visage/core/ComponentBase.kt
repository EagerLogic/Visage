package visage.core

import org.w3c.dom.Node

@DslMarker
annotation class ComponentMarker

interface IDomNode {

    fun createNode(): Node
    fun updateNode(node: Node, oldComponent: AComponent<*>)

}

@ComponentMarker
interface IChildScope {
    fun addChild(child: AComponent<*>)
    fun <S : Any, C : AComponent<S>> registerComponent(child: C, init: C.() -> Unit)
}

interface Components : IChildScope

internal class ChildProxy(private val childScope: IChildScope) : Components {

    override fun addChild(child: AComponent<*>) {
        childScope.addChild(child)
    }

    override fun <S : Any, C : AComponent<S>> registerComponent(child: C, init: C.() -> Unit) {
        childScope.registerComponent(child, init)
    }

}

@ComponentMarker
abstract class AComponent<GState : Any> : IChildScope {

    lateinit var state: GState

    internal var _children_internal = mutableListOf<AComponent<*>>()
    internal var _children_external: List<AComponent<*>>? = null
    internal var _dom_node: Node? = null
    internal var _invalid: Boolean = true

    val typeClass: String = this::class.simpleName ?: "Unknown"

    internal val type: String
        get() {
            if (!this::state.isInitialized) {
                this.state = this.initState()
            }
            return "${this::class.simpleName}<${this.state::class.simpleName}>"
        }

    private val internalComponents: Components = object : Components {
        override fun addChild(child: AComponent<*>) {
            this@AComponent.addChild(child)
        }

        override fun <S : Any, C : AComponent<S>> registerComponent(child: C, init: C.() -> Unit) {
            this@AComponent.registerComponent(child, init)
        }

    }

    protected fun internalChildren(init: Components.() -> Unit) {
        this.internalComponents.init()
    }

    protected abstract fun initState(): GState

    final override fun addChild(child: AComponent<*>) {
        this._children_internal.add(child)
    }

    final override fun <S : Any, C : AComponent<S>> registerComponent(child: C, init: C.() -> Unit) {
        child.init()
        this._children_internal.add(child)
    }

    fun refresh() {
        // TODO fix partial render
//        this._invalid = true
//        Visage.render()
        Visage.rerender()
    }

    abstract fun Components.render(children: List<AComponent<*>>)

    internal fun doRender() {
        val externalChildren = if (this._children_external == null) {
            this._children_external = this._children_internal
            this._children_internal
        } else {
            this._children_external!!
        }
        this._children_internal = mutableListOf()
        ChildProxy(this).render(externalChildren)
        this._invalid = false
    }

    internal fun _init_state_internal() {
        this.state = this.initState()
    }

    internal fun _restore_state_internal(oldState: GState) {
        this.state = oldState
    }

    internal fun _read_state_internal(): GState {
        return this.state
    }

    internal fun getFlatNodes(): List<Node> {
        val res = mutableListOf<Node>()
        if (this._dom_node != null) {
            res.add(this._dom_node!!)
        } else {
            for (child in this._children_internal) {
                res.addAll(child.getFlatNodes())
            }
        }
        return res
    }

    open fun onComponentWillMount() {}

    open fun onComponentDidMount() {}

    open fun onComponentWillUnmount() {}

    open fun onComponentDidUnmount() {}

    open fun onComponentWillUpdate() {}

    open fun onComponentDidUpdate() {}

    open fun onRestoreState(oldState: GState): GState? {
        return oldState
    }

}

abstract class APureComponent : AComponent<Unit>() {
    final override fun initState() {

    }
}

abstract class AComposite<GState : Any>() : AComponent<GState>(), Components {

}

abstract class APureComposite : AComposite<Unit>() {
    final override fun initState() {

    }
}


