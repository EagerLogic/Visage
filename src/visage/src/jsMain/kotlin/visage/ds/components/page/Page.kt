package visage.ds.components.page

import visage.core.AComponent
import visage.core.Components
import visage.dom.CssClass
import visage.dom.div
import visage.ds.components.*
import visage.ds.utils.ERenderMode
import visage.ds.utils.RenderMode

fun Components.Page(init: CPage.() -> Unit) =
    this.registerComponent(CPage(), init)

class CPage() : AComponent<CPage.Companion.State>() {

    companion object {
        class State {
            var tabIndex = 0;
        }
    }

    var isLoading: Boolean = false
    private var head: Header? = null
    private var body: (CPageBody.() -> Unit)? = null
    private var tabs: MutableList<CTab> = mutableListOf()

    override fun initState(): State {
        return State()
    }

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = rootStyle

            // header
            if (this@CPage.head != null) {
                div {
                    classes = headerRootStyle
                    if (SideMenu.isOpened && RenderMode.current == ERenderMode.Full) {
                        style.left = "280px"
                    }
                    val header = CHeader(
                        title = this@CPage.head!!.title,
                        small = false,
                        menuVisible = true,
                        tabs = this@CPage.tabs,
                        selectedTabIndex = this@CPage.state.tabIndex
                    )
                    header.onSelectedTabChanged = {
                        this@CPage.state.tabIndex = it
                        this@CPage.refresh()
                    }
                    this.registerComponent(header, this@CPage.head!!.init)
                }
            }

            // loading indicator
            if (this@CPage.isLoading) {
                div {
                    classes = loadingIndicatorRootStyle
                    if (SideMenu.isOpened && RenderMode.current == ERenderMode.Full) {
                        style.left = "280px"
                    }

                    HorizontalLoadIndicator(null) {
                        height = 3
                    }
                }
            }

            // top padding
            if (this@CPage.head != null) {
                if (this@CPage.tabs.size < 2) {
                    style.paddingTop = "96px"
                } else {
                    style.paddingTop = "144px"
                }
            }

            // body
            if (!this@CPage.tabs.isEmpty()) {
                this@CPage.tabs.forEachIndexed() { index, tab ->
                    if (index == this@CPage.state.tabIndex) {
                        this.registerComponent(CPageBody(), tab.body)
                    }
                }
            } else if (this@CPage.body != null) {
                this.registerComponent(CPageBody(), this@CPage.body!!)
            }
        }
    }

    fun head(title: String, init: (CHeader.() -> Unit) = {}) {
        this.head = Header(title, init)
    }

    fun body(body: CPageBody.() -> Unit) {
        this.body = body
    }

    fun tab(title: String, body: CPageBody.() -> Unit) {
        tabs.add(CTab(title, body))
    }

}

private val rootStyle by CssClass {
    width = "100%"
    minWidth = width
    minHeight = height
    paddingLeft = "32px"
    paddingTop = "32px"
    paddingRight = "32px"
    paddingBottom = "32px"
}

private val headerRootStyle by CssClass {
    position = "fixed"
    left = "0px"
    top = "0px"
    right = "0px"
    zIndex = "900"
    display = "flex"
    flexDirection = "column"
    boxShadow = "1px 1px 8px rgba(0,0,0, 20%)"
}

private val loadingIndicatorRootStyle by CssClass {
    position = "fixed"
    left = "0px"
    top = "0px"
    right = "0px"
    height = "3px"
    zIndex = "950"
}



