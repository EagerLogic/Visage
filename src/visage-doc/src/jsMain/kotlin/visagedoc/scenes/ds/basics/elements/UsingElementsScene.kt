package visagedoc.scenes.ds.basics.elements

import kotlinx.browser.window
import visage.core.Components
import visage.dom.button
import visage.ds.components.AttentionMessage
import visage.ds.components.BasePageContent
import visage.ds.components.EAttentionMessageType
import visagedoc.components.DocPage
import visagedoc.components.codeBlock
import visagedoc.components.p
import visagedoc.components.title

fun Components.UsingElementsScene() = this.registerFunctionalComponent({}) {
    BasePageContent("Basics / Elements / Using elements") {
        DocPage {
            title("Structure of an Element")
            p {
                +"""
                    An Element (and also a Component) is just a 'plain' Kotlin function which we call when we want to render it.
                    It has a name (like: div) followed by parameters inside parentheses, and an init block. This is what we call the builder function.
                    This is the entry point of an element (and component).
                """
            }
            p {
                AttentionMessage(EAttentionMessageType.Info, "Note:", """
                    An Element is just a special kind of Component. In usage perspective, there isn't any difference between the too.
                """)
            }
            p {
                +"Consider the following example:"
            }
            codeBlock("""
div() {
    img(src = "/image.png") {
        alt = "A cool image"
    }
}
            """)
            p {
                +"""
                    Every function has a name, and every function call is starts with the name of the function. In this case
                    'div' and 'img' are the builder functions of the div and the img Element.
                """
            }
            p {
                +"""
                    After the function name, comes the parameters (if any) in parentheses. Most of the casees, parameters are
                     mandatory attributes, which must be set to create an Element. Not every Element has a parameter and in this case
                     the parentheses can be omitted. In the previous example, div does not have any parameter, so we can safely 
                     omit the parentheses and in this case the code looks like this:
                """
            }
            codeBlock("""
div {
    img(src = "/image.png") {
        alt = "A cool image"
    }
}
            """)

            p {
                +"""
                    After the parameters, comes the init block. This is just a lambda as the last parameter of the builder function, but thanks to 
                    Kotlin, if a lambda is the last parameter of the function, than this lambda can be written outside of the parentheses, as we do in
                    the img Element.
                """
            }
            p {
                +"""
                    In the init block, you can initialize the component. You can define attribute values, attach event listeners,
                    and put children in that Element. Not every Element (and Component) has an init block, and there are some 
                    situations when you don't want to put anything inside the init block. In this case the init block can be omitted.
                """
            }
            p {
                +"""
                    For example, if we don't want to set the 'alt' attribute of the 'img' element, than the init block, can be omitted,
                    and the previous code may looks like this:
                """
            }
            codeBlock("""
div {
    img(src = "/image.png")
}
            """)
            p {
                AttentionMessage(EAttentionMessageType.Warning, "Note:", """
                    However empty parentheses and init blocks can be omitted, you can not omit both. One of those must be present.
                """)
            }

            title("Built-in and other Elements")
            p {
                +"""
                    Visage has built in elements for the most common HTML elements (like div, img, b, etc...), but not all of them.
                    If you want to use a HTML element which does not have a built-in Visage representation, you can use the 'tag' element.
                    In the tag element you can define what tag will be rendered.
                """
            }
            p {
                +"For example, if you want to render a 'canvas' element which does not have a built in Visage element, you can do it like this:"
            }
            codeBlock("""
tag("canvas") {
      
}
            """)

            title("Text elements")
            p {
                +"""
                    Now we know how to create and render elements, but there are many situations when we want to put some text 
                    in an element as a HTML Text node. This can be achieved by using the unary plus operator on a String which we
                    want to add as a child Text node.
                """
            }
            p {
                +"""
                    For example, if we want to put the text 'Hello, world!' inside a h3 (or any other) element, ew can do it like this:
                """
            }
            codeBlock("""
h3 {
    +"Hello, world!"
}
            """)

            title("Accessing Element Attributes")
            p {
                +"""
                    Visage defines the most common attributes of an element, which you can set in the init block.
                    For example, if we want to set the 'name', 'type', and 'placeholder' of an 'input' element, we can do it like this:
                """
            }
            codeBlock("""
input {
    type = "text"
    name = "email"
    placeholder = "johndoe@email.com"
}
            """)
            p {
                +"""
                    As I mentioned, not all the available attributes are defined in the elements. If you want to set an 
                    attribute which isn't defined in the element, you can still do it using the 'attr' attribute.
                    For example if we want to als set the 'maxlength' attribute, which isn't defined in the input element, than we can do it like this:
                """
            }
            codeBlock("""
input {
    type = "text"
    name = "email"
    placeholder = "johndoe@email.com"
    attr["maxlength"] = "35"
}
            """)
            p {
                AttentionMessage(EAttentionMessageType.Info, "Note:", """
                    Every attribute is stored as a String. Even if it's a number or boolean attribute in HTML, you need to pass them as string.
                """)
            }

            title("Using Events")
            p {
                +"""
                    Some elements can fire events when some event is happened like 'onClick' or 'onMouseMove'. We can
                    listen and react to these events by attaching event listeners to them. The predefined events can be
                    accessed by the 'events' attribute of the element.
                """
            }
            p {
                +"""
                    For example, if we want to display an alert every time when a button clicked, than we can attach an event listener
                    to the 'onClick' event of the 'button' element, and write what need to happen when it happens like this:
                """
            }
            codeBlock("""
button {
    +"Click me"

    events.onClick = {
        window.alert("Thanks for clicking me!")
    }
}
            """)
            p {
                +"Which will render the folowing button:"
            }
            p {
                button {
                    +"Click me"

                    events.onClick = {
                        window.alert("Thanks for clicking me!")
                    }
                }
            }

            p {
                +"""
                    Visage defines the most common events for Elements, but not all of them. If you want to use an
                    undefined event on an Element, you can do it using the events["eventname"] = {} syntax.
                """
            }
            p {
                +"""
                    For example, if we want to listen to 'dragstart' events on a div Element, than we can do it like this:
                """
            }
            codeBlock("""
div {
    events["dragstart"] = {
        console.log("Drag started")    
    }
}
            """)
            p {
                AttentionMessage(EAttentionMessageType.Warning, "Note:", """
                    Using the events[""] syntax, the 'on' prefix must be omitted from the event name, and must be lowercase.
                    For example: onClick -> events["click"], onDragStart -> events["dragstart"].
                """)
            }

        }
    }
}