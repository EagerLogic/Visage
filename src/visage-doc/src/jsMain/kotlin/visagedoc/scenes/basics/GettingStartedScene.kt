//package visagedoc.scenes.basics
//
//import visage.core.AComponent
//import visage.core.APureComponent
//import visage.core.Components
//import visage.ds.components.page.PageContent
//import visagedoc.components.DocPage
//import visagedoc.components.codeBlock
//import visagedoc.components.p
//import visagedoc.components.title
//import visagedoc.visageVersion
//
//class CGettingStartedScene : APureComponent() {
//    override fun Components.render(children: List<AComponent<*>>) {
//        PageContent("Basics / Getting started") {
//            DocPage {
//                title("Installation")
//                p {
//                    +"""
//                        All you need to do to use Visage in your Kotlin/JS frontend project is to define a dependency in your build.gradle.kts
//                    """
//                }
//                codeBlock("""
//val jsMain by getting {
//    dependencies {
//        implementation("io.github.eagerlogic:visage-js:$visageVersion")
//    }
//}
//                """)
//
//                title("Setting up")
//                p {
//                    +"""
//                        Now you can access the classes of the Visage js library. It's time to set it up. First you need a html file which servers
//                        your javascript file with the root div of Visage. All the other required stuffs will be set up by Visage.
//                    """
//                }
//                codeBlock("""
//<!DOCTYPE html>
//<html>
//
//    <head>
//        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
//        <title>Visage example page</title>
//    </head>
//
//    <body>
//        <!-- The root container. Visage will render all the stuffs in this div. -->
//        <div id="root" style="width: 100%; height: 100%;"></div>
//
//        <!-- Your application's JS file. -->
//        <script src="/app.js" type="text/javascript"></script>
//    </body>
//
//</html>
//                """)
//
//                p {
//                    +"""
//                        Now you are ready to spin up Visage. For this, you need to call the Visage.init(...) method which is the
//                        entry point of Visage. You will define what to render, inside of the lambda. Of course we are creating a Hello world!
//                        Place the following code in your main function:
//                    """
//                }
//                codeBlock("""
//fun main() {
//    console.log("Visage site loading...")
//    window.onload = {
//        startVisage()
//    }
//}
//
//fun startVisage() {
//    console.log("Visage starting...")
//
//    // initializing visage
//    Visage.init(window.document.getElementById("root") as HTMLElement) {
//        // define what to render
//        h1 { +"Hello, world!" }
//    }
//
//    // rendering
//    Visage.render()
//}
//                """)
//
//                p {
//                    +"""
//                        If you load your html in a browser and everything went well, than you can see the "Hello, world!" message in your browser.
//                        Now move on to the next topic to learn the basics of Visage.
//                    """
//                }
//                p {
//                    +"""
//                        Please note that there is an unary plus (+) operator before the 'Hello, world!' string. It's
//                        not a typo, it's an important part of the code. Later we will discuss it, at this time you just
//                        need to keep it there.
//                    """
//                }
//            }
//        }
//    }
//}
//
//fun Components.GettingStartedScene() = this.registerComponent(CGettingStartedScene(), {  })