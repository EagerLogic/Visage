package visage.core

import kotlinx.browser.window
import org.w3c.dom.Node
import visage.dom.CTag

internal class Reconciler private constructor() {

    companion object {

        fun reconcileComponent(comp: AComponent<*>, parentNode: Node, beforeNode: Node?) {
            if (comp._invalid) {
                val oldChildren = comp._children_internal
                comp.doRender()
                var newParentNode: Node = parentNode
                var newBeforeNode: Node? = beforeNode
                if (comp is IDomNode) {
                    if (comp._dom_node == null) {
                        comp._dom_node = comp.createNode()
                    }
                    newParentNode = comp._dom_node!!
                    newBeforeNode = null
                }
                mergeComponents(oldChildren as List<AComponent<Any>>, comp._children_internal as List<AComponent<Any>>, newParentNode, newBeforeNode, comp)
            } else {
                var newParentNode: Node = parentNode
                var newBeforeNode: Node? = beforeNode
                if (comp is IDomNode) {
                    newParentNode = comp._dom_node!!
                    newBeforeNode = null
                }
                comp._children_internal.forEach {
                    reconcileComponent(it, newParentNode, newBeforeNode)
                }
            }
        }

        fun mergeComponents(oldComponents: List<AComponent<Any>>, newComponents: List<AComponent<Any>>, parentNode: Node, beforeNode: Node?, parentComponent: AComponent<*>?) {
            val uows = mutableListOf<Uow>()

            for ((i, newComp) in newComponents.withIndex()) {
                if (i >= oldComponents.size) {
                    // insert
                    newComp._init_state_internal()
                    newComp.doRender()
                    uows.add(UowInsert(newComp, parentComponent))
                } else {
                    val oldComp = oldComponents[i]
                    var isUpdate = false
                    if (newComp._type == oldComp._type) {
                        if (newComp !is CTag || oldComp is CTag && newComp.isSameTag(oldComp)) {
                            var state: Any? = oldComp._read_state_internal()
                            state = newComp.onRestoreState(state!!)
                            if (state != null) {
                                isUpdate = true
                                newComp._restore_state_internal(state)
                            }
                        }
                    }
                    newComp.doRender()

                    if (isUpdate) {
                        // update
                        uows.add(UowUpdate(oldComp, newComp))
                    } else {
                        //replace
                        uows.add(UowDelete(oldComp))
                        uows.add(UowInsert(newComp, parentComponent))
                    }
                }
            }

            if (oldComponents.size > newComponents.size) {
                for (i in newComponents.size until oldComponents.size) {
                    // delete
                    uows.add(UowDelete(oldComponents[i]))
                }
            }

            var newBeforeNode: Node? = beforeNode
            for (i in (uows.size - 1) downTo 0) {
                val uow = uows[i]
                newBeforeNode = when (uow){
                    is UowInsert -> {
                        uow.insert(parentNode, newBeforeNode)
                        calculateNewBeforeNode(newBeforeNode, uow.newComp)
                    }
                    is UowUpdate -> {
                        uow.update(parentNode, newBeforeNode)
                        calculateNewBeforeNode(newBeforeNode, uow.newComp)
                    }
                    is UowDelete -> {
                        uow.delete(parentNode)
                        newBeforeNode
                    }
                }
            }
        }

        private fun calculateNewBeforeNode(prevBeforeNode: Node?, comp: AComponent<*>): Node? {
            return comp.getFlatNodes().firstOrNull() ?: prevBeforeNode
        }

    }

}

private sealed class Uow

private class UowInsert(val newComp: AComponent<*>, val parentComponent: AComponent<*>?): Uow() {

    fun insert(parentNode: Node, beforeNode: Node?) {
        newComp.onComponentWillMount()

        var newParent = parentNode
        var newBeforeNode = beforeNode
        if (newComp is IDomNode) {
            val node = newComp.createNode()
            if (beforeNode == null) {
                parentNode.appendChild(node)
            } else {
                parentNode.insertBefore(node, beforeNode)
            }
            newComp._dom_node = node
            newBeforeNode = null
            newParent = node
        }

        window.setTimeout({
            newComp.onComponentDidMount()
        }, 0)

        Reconciler.mergeComponents(listOf(), (newComp._children_internal.unsafeCast<List<AComponent<Any>>>()), newParent, newBeforeNode, newComp)
    }

}

private class UowUpdate(val oldComp: AComponent<*>, val newComp: AComponent<*>): Uow() {

    fun update(parentNode: Node, beforeNode: Node?) {

        newComp.onComponentWillUpdate()

        var newParent = parentNode
        var newBeforeNode = beforeNode
        if (this.newComp is IDomNode) {
            this.newComp._dom_node = this.oldComp._dom_node
            this.newComp.updateNode(this.newComp._dom_node!!, this.oldComp)
            newBeforeNode = null
            newParent = this.newComp._dom_node!!
        }

        window.setTimeout({
            newComp.onComponentDidUpdate()
        }, 0)

        Reconciler.mergeComponents(this.oldComp._children_internal.unsafeCast<List<AComponent<Any>>>(), this.newComp._children_internal.unsafeCast<List<AComponent<Any>>>(), newParent, newBeforeNode, newComp)
    }

}

private class UowDelete(val oldComp: AComponent<*>): Uow() {

    fun delete(parentNode: Node) {
        oldComp.onComponentWillUnmount()

        var newParent = parentNode
        if (oldComp._dom_node != null) {
            newParent = oldComp._dom_node!!
        }

        Reconciler.mergeComponents(oldComp._children_internal.unsafeCast<List<AComponent<Any>>>(), listOf(), newParent, null, null)

        if (oldComp._dom_node != null) {
            parentNode.removeChild(oldComp._dom_node!!)
        }

        window.setTimeout({
            oldComp.onComponentDidUnmount()
        }, 0)


    }

}
