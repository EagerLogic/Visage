//package visagedoc.scenes.ds.basics.elements
//
//import visage.core.Components
//import visage.ds.components.AttentionMessage
//import visage.ds.components.EAttentionMessageType
//import visage.ds.components.PageContent
//import visagedoc.components.*
//
//fun Components.StylingElementsScene() = this.registerFunctionalComponent({}) {
//    PageContent("Basics / Elements / Styling elements") {
//        DocPage {
//            title("Using the style attribute")
//            p {
//                +"""
//                    As you already know from HTML, every element has a 'style' attribute which you can use to define some
//                    custom style for the element. You can access this style properties using the 'style' attribute of an Element.
//                """
//            }
//            p {
//                +"""
//                    For example, if you want to set a div's background red and font color to white, you can do it like this:
//                """
//            }
//            codeBlock("""
//div {
//    style.backgroundColor = "red"
//    style.color = "#fff"
//}
//            """)
//            p {
//                +"""
//                    If you want to set multiple style attributes, you can use Kotlin's built-in 'apply' function to make it shorter like this:
//                """
//            }
//            codeBlock("""
//div {
//    style.apply {
//        backgroundColor = "red"
//        color = "#fff"
//    }
//}
//            """)
//            p {
//                +"""
//                    Every style attribute is a String, so you must put the values of the style attribute as a String.
//                """
//            }
//            p {
//                AttentionMessage(EAttentionMessageType.Warning, "Note", """
//                    You can also set direectly the attr["style"] attribute of the element, but this is NOT recommended and
//                    may deprecated in future versions, because it's much slower than using the 'style' attribute and not typesafe.
//                    ALWAYS use the 'style' attribute of the element if possible.
//                """)
//            }
//
//            subTitle("Accessing undefined style attributes")
//            p {
//                +"""
//                    Visage defines the most common style attributes, but not all of them. Cause the style attribute is just
//                    a fancy 'Map<String, String>' you can use any undefined style attribute by using it's indexer syntax.
//                """
//            }
//            p {
//                +"""
//                    For example, if you also want to set the undefined 'scroll-padding' style attribute of the previous div,
//                    you can do it like this:
//                """
//            }
//            codeBlock("""
//div {
//    style.backgroundColor = "red"
//    style.color = "#fff"
//    style["scroll-padding"] = "2px"
//}
//            """)
//            p {
//                +"Or using the 'apply' syntax:"
//            }
//            codeBlock("""
//div {
//    style.apply {
//        backgroundColor = "red"
//        color = "#fff"
//        this["scroll-padding"] = "2px"
//    }
//}
//            """)
//            p {
//                AttentionMessage(EAttentionMessageType.Warning, "Note:", """
//                    Please note that using the indexer syntax, you must use the hyphen-case ("scroll-padding" instead of "scrollPadding") name of the attribute, just like
//                    in CSS.
//                """)
//            }
//
//        }
//    }
//}