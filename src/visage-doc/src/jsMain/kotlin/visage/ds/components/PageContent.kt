package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.APureComposite
import visage.core.Components
import visage.dom.Css
import visage.dom.Listener
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight
import visage.ds.utils.ERenderMode
import visage.ds.utils.RenderMode

fun Components.PageContent(init: CPageContent.() -> Unit) =
    this.registerComponent(CPageContent(), init)

class CPageContent() : AComponent<CPageContent.Companion.State>() {

    companion object {
        class State {
            var tabIndex = 0;
        }
    }

    var isLoading: Boolean = false
    private var head: PageHeader? = null
    private var body: (CPageBody.() -> Unit)? = null
    private var tabs: MutableList<CTab> = mutableListOf()

    override fun initState(): State {
        return State()
    }

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = rootStyle

            // header
            if (this@CPageContent.head != null) {
                div {
                    classes = headerRootStyle
                    if (SideMenu.isOpened && RenderMode.current == ERenderMode.Full) {
                        style.left = "280px"
                    }
                    val header = CPageHeader(this@CPageContent.head!!.title, this@CPageContent.tabs, this@CPageContent.state.tabIndex)
                    header.onSelectedTabChanged = {
                        this@CPageContent.state.tabIndex = it
                        this@CPageContent.refresh()
                    }
                    this.registerComponent(header, this@CPageContent.head!!.init)
                }
            }

            // loading indicator
            if (this@CPageContent.isLoading) {
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
            if (this@CPageContent.head != null) {
                if (this@CPageContent.tabs.size < 2) {
                    style.paddingTop = "96px"
                } else {
                    style.paddingTop = "144px"
                }
            }

            // body
            if (!this@CPageContent.tabs.isEmpty()) {
                this@CPageContent.tabs.forEachIndexed() { index, tab ->
                    if (index == this@CPageContent.state.tabIndex) {
                        this.registerComponent(CPageBody(), tab.body)
                    }
                }
            } else if (this@CPageContent.body != null) {
                this.registerComponent(CPageBody(), this@CPageContent.body!!)
            }
        }
    }

    fun head(title: String, init: (CPageHeader.() -> Unit) = {}) {
        this.head = PageHeader(title, init)
    }

    fun body(body: CPageBody.() -> Unit) {
        this.body = body
    }

    fun tab(title: String, body: CPageBody.() -> Unit) {
        tabs.add(CTab(title, body))
    }

}

class PageHeader(val title: String, val init: (CPageHeader.() -> Unit) = {})

private val rootStyle = Css.createClass {
    width = "100%"
    minWidth = width
    minHeight = height
    paddingLeft = "32px"
    paddingTop = "32px"
    paddingRight = "32px"
    paddingBottom = "32px"
}

private val headerRootStyle = Css.createClass {
    position = "fixed"
    left = "0px"
    top = "0px"
    right = "0px"
    zIndex = "900"
    display = "flex"
    flexDirection = "column"
}

private val loadingIndicatorRootStyle = Css.createClass {
    position = "fixed"
    left = "0px"
    top = "0px"
    right = "0px"
    height = "3px"
    zIndex = "950"
}

// -----------------------------------------------------------------------------

class CPageHeader(val title: String, private val tabs: List<CTab>, private val selectedTabIndex: Int) : APureComponent() {

    internal var onSelectedTabChanged: Listener<Int>? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = mainHeaderStyle

            IconButton("menu") {
                variant = EButtonVariant.Link
                color = EButtonColor.Secondary
                onClick = this@CPageHeader::onMenuButtonClicked
                size = EButtonSize.Large
            }

            div { style.width = "14px"}

            div {
                classes = titleStyle
                +this@CPageHeader.title
            }

            for (child in children) {
                div {
                    style.width = "16px"
                    style.minWidth = "16px"
                    style.maxWidth = "16px"
                }
                +child
            }
        }
        if (this@CPageHeader.tabs.size > 1) {
            div {
                classes = subHeaderStyle

                this@CPageHeader.tabs.forEachIndexed { index, tab ->
                    div {
                        style.apply {
                            width = "16px"
                            minWidth = width
                            maxWidth = width
                        }
                    }
                    Tab(tab.title, index == this@CPageHeader.selectedTabIndex) {
                        this@CPageHeader.onSelectedTabChanged?.invoke(index)
                    }
                    div {
                        style.apply {
                            width = "16px"
                            minWidth = width
                            maxWidth = width
                        }
                    }

                }
            }
        }
    }

    fun button(icon: String, title: String, init: (CButton.() -> Unit) = {}) =
        this.registerComponent(CButton(if (RenderMode.compressed) null else title, icon), init)
    fun button(icon: String, init: (CButton.() -> Unit) = {}) = this.registerComponent(CButton(null, icon), init)

    private fun onMenuButtonClicked() {
        if (SideMenu.isOpened) {
            SideMenu.close()
        } else {
            SideMenu.open()
        }
    }

}

val mainHeaderStyle = Css.createClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    height = "64px"
    minHeight = height
    maxHeight = height
    overflow = "hidden"
    display = "flex"
    alignItems = "center"
    paddingLeft = "14px"
    paddingRight = "16px"
    backgroundColor = Skin.palette.mainHeaderBgColor
}

val titleStyle = Css.createClass {
    flexGrow = "1"
    overflow = "hidden"
    textOverflow = "ellipsis"
    color = Skin.palette.strongTextColor
    fontSize = "18px"
    fontWeight = EFontWeight.SemiBold.cssValue
}

val subHeaderStyle = Css.createClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    height = "48px"
    minHeight = height
    maxHeight = height
    overflow = "hidden"
    display = "flex"
    //paddingLeft = "16px"
    paddingRight = "16px"
    backgroundColor = Skin.palette.mainHeaderBgColor
}

// -------------------------------------------------------------------------------------------------

class CTab(val title: String, val body: CPageBody.() -> Unit) {

}

// -------------------------------------------------------------------------------------------------

class CPageBody() : APureComposite() {
    override fun Components.render(children: List<AComponent<*>>) {
        children.forEach {
            +it
        }
    }

}

// -------------------------------------------------------------------------------------------------

private fun Components.Tab(title: String, isSelected: Boolean, onClick: () -> Unit) = this.registerFunctionalComponent({}) {
    div {
        classes = tabStyle
        if (isSelected) {
            style.apply {
                color = Skin.palette.primaryColor
                borderBottom = "3px solid ${Skin.palette.primaryColor}"
            }

        }

        events.onClick = {
            onClick()
        }

        div {
            style.display = "inline-block"

            +title
        }
    }
}

private val tabStyle = Css.createClass {
    height = "100%"
    minHeight = height
    maxHeight = height
    flexGrow = "0"
    flexShrink = "0"
    borderTop = "3px solid transparent"
    borderBottom = "3px solid transparent"
    fontSize = "18px"
    fontWeight = EFontWeight.SemiBold.cssValue
    color = Skin.palette.weekTextColor
    cursor = "pointer"
    overflow = "hidden"
    textOverflow = "ellipsis"
    display = "flex"
    alignItems = "center"
}



