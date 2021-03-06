package visagedoc.scenes.ds.basics

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.text
import visage.ds.components.BasePageContent
import visagedoc.components.DocPage
import visagedoc.components.codeBlock
import visagedoc.components.p
import visagedoc.components.title

class CDomComponentsScene : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        BasePageContent("Basics / DOM Components") {
            DocPage {
                title("Introduction")
                p {
                    + """
                        The most basic components of Visage are the DOM components. These components represents real DOM elements 
                        like DIV in a virtual way. Most of the times you will be using the predefined or third party components, but those 
                        are built on top of and wraps DOM components too. DOM components are useful if you want to implement your own Component,
                        or do something which isn't available using existing components.
                        """
                }

                title("Tag")
                p {
                    + """
                        The root of all the DOM components is the 'tag' component. The tag component represents a HTML 
                        element in the DOM. You can set attributes and events on a tag, and also embed child tags in it.
                        The tag component had a required parameter which is the name of the element like 'div' or 
                        'input', etc...The following simple example shows how to use it:
                        """
                }
                codeBlock(
                    """
Visage.init(window.document.getElementById("root") as HTMLElement) {
    tag("div") {
        tag("input")
        tag("br")
        tag("button")
    }
}
                    """
                )
                p {
                    + """
                        The above code snippet creates a DIV and put an INPUT a BR and a BUTTON element inside the DIV. 
                        As you can see, you can embed child tags inside a tag by using the tag's lambda function. Couse 
                        these lambdas are just real functions, you can safely use any kotlin statement like 'if' or 'for'
                        or fields and variables to dynamically or conditionally render some elements like this:
                        """

                }
                codeBlock(
                    """
tag("div") {
    tag("input")
    if (someCondition) {
        tag("br")
        tag("button")
    }
} 
                    """
                )
                p {
                    + """
                         In this case, the BR and the BUTTON elements are only rendered when someCondition evaluates to true.
                         Basically, all visage components are just functions too which ads child components if called (later we will talk more about this),
                         so you can do anything inside these lambdas what you can do in a real function. Use statements, define local variables, use ifs and loops or
                         fields, etc... This will produces easily readable declarative code.
                         
                        """
                }

                title("Attributes")
                p {
                    + """
                        Ofcourse you can set attributes of the tag-s inside it's init block, which translates to HTML 
                        element attributes. There are some predefined attributes which can be accessed directly, but you can 
                        set any attributes you want like this:
                        """

                }
                codeBlock(
                    """
tag("form") {
    // built in attributes can be accessible using simple fields
    attr.id = "myform"
    
    // any other attribute can be accessed like this attr["attributeName"] = "attributeValue"
    attr["action"] = "/index.vsg"
    attr["method"] = "post"
    
    tag("input") {
        attr["type"] = "text"
        attr["name"] = "email"
    }
}     
                    """
                )
            }
        }
    }
}

fun Components.DomComponentsScene() = this.registerComponent(CDomComponentsScene(), {})