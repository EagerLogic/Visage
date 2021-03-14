//package visagedoc.scenes.ds.basics.elements
//
//import visage.core.Components
//import visage.ds.components.AttentionMessage
//import visage.ds.components.EAttentionMessageType
//import visage.ds.components.PageContent
//import visagedoc.components.DocPage
//import visagedoc.components.codeBlock
//import visagedoc.components.p
//import visagedoc.components.title
//
//fun Components.RenderingElementsScene() = this.registerFunctionalComponent({}) {
//    PageContent("Basics / Elements / Rendering elements") {
//        DocPage {
//            title("Introduction")
//            p {
//                AttentionMessage(EAttentionMessageType.Info, "Note", """
//                    Most of the cases, you don't need to use elements, instead you will use the built in or third party
//                    component libraries, to build your application, but Components are made of elements, so you need to
//                    understand the concept behind to be able to create your own custom components later and to understand
//                    what is going on in the background.
//                """)
//            }
//            p {
//                +"""
//                    Elements are the smallest building blocks of Visage UI. An element represents a HTML element, and
//                     describes what you want to see on the screen:
//                """
//            }
//            codeBlock(
//                """
//h1 { +"Hello, world!" }
//                """
//            )
//            p {
//                +"""
//                    Unlike browser DOM elements, Visage elements are just plain objects, and are cheap to create.
//                    Visage takes care of updating the DOM to match the Visage elements.
//                """
//            }
//
//            title("Rendering an Element into the DOM")
//            p {
//                +"Let’s say there is a <div> somewhere in your HTML file:"
//            }
//            codeBlock(
//                """
//<div id="root"></div>
//                """
//            )
//            p {
//                +"""
//                    This is the a “root” DOM node, everything inside it will be managed by Visage UI.
//                    You can tell Visage what to render by passing it into the Visage.init() function. Visage always render
//                    the content of the Visage.init() function. Of course you can put any kotlin expression to
//                    dynamically render elements, by this you can change what is rendered over time.
//                """
//            }
//            codeBlock(
//                """
//// the first parameter is the root DOM node to render into
//Visage.init(window.document.getElementById("root") as HTMLElement) {
//
//    // define what to render in this lambda
//    h1 { +"Hello, world" }
//
//}
//                """
//            )
//            p {
//                +"""
//                    Now you defined what to render. It's time to render. Every time you need to re-render your
//                    scene, you need to call the Visage.rerender() function.
//                """
//            }
//            codeBlock("Visage.rerender()")
//
//            title("Updating rendered elements")
//            p {
//                +"""
//                    Visage elements are immutabel. Once you created an element, you can't change its children or attributes.
//                    The only way to change the screen is to rerender something else on your screen.
//                """
//            }
//            p {
//                +"""
//                    Consider this ticking clock example:
//                """
//            }
//            codeBlock("""
//Visage.init(window.document.getElementById("root") as HTMLElement) {
//    div {
//        h1 { +"Hello, world" }
//        h2 { +"It is ${'$'}{Date().toLocaleTimeString()}." }
//    }
//}
//window.setInterval({ Visage.rerender() }, 1000)
//            """)
//            p {
//                +"""
//                    It calls Visage.rerender() every second from a setInterval() callback, which makes the screen rerendered
//                    with the new time, recreating all the elements with a new content (the current time).
//                """
//            }
//            p {
//                AttentionMessage(EAttentionMessageType.Info, "Note", """
//                    Recreating all the Visage elements, does not mean that every time the full Browser DOM is recreated.
//                    After building the new Virtual DOM, Visage takes care to only update in the DOM, what is changed.
//                """)
//            }
//            p {
//                AttentionMessage(EAttentionMessageType.Warning, "Note:", """
//                    In practice, most Visage apps only call Visage.rerender() once. In the next sections we will learn how such
//                    code gets encapsulated into stateful components.
//                """)
//            }
//        }
//    }
//}