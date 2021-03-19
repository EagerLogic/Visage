package visagedoc.scenes.ds.components

import visage.core.Components
import visage.dom.div
import visage.ds.components.AttentionMessage
import visage.ds.components.EAttentionMessageType
import visage.ds.components.page.Page
import visagedoc.components.DocPage
import visagedoc.components.codeBlock
import visagedoc.components.p
import visagedoc.components.title

fun Components.AttentionMessageScene() = this.registerFunctionalComponent({}) {
    Page {
        head("Components / AttentionMessage")
        body {
            DocPage {
                title("AttentionMessage")
                p {
                    AttentionMessage(EAttentionMessageType.Info, "Info message")
                    div { style.height = "16px" }
                    AttentionMessage(EAttentionMessageType.Success, "Success message")
                    div { style.height = "16px" }
                    AttentionMessage(EAttentionMessageType.Warning, "Warning message")
                    div { style.height = "16px" }
                    AttentionMessage(EAttentionMessageType.Danger, "Danger message")
                }
                codeBlock("""
AttentionMessage(EAttentionMessageType.Info, "Info message")
AttentionMessage(EAttentionMessageType.Success, "Success message")
AttentionMessage(EAttentionMessageType.Warning, "Warning message")
AttentionMessage(EAttentionMessageType.Danger, "Danger message")
                """)
            }
        }
    }
}