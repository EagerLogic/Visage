//package visagedoc.scenes.basics
//
//import visage.core.Components
//import visage.ds.components.page.PageContent
//import visagedoc.components.DocPage
//import visagedoc.components.codeBlock
//import visagedoc.components.p
//import visagedoc.components.title
//
//fun Components.TypeSafeBuildersScene() = this.registerFunctionalComponent({}) {
//    PageContent("Basics / Type-safe builders") {
//        DocPage {
//            title("Introduction")
//            p {
//                +"""
//                    Type-safe builders allow creating Kotlin-based domain-specific languages (DSLs) suitable for
//                    building complex hierarchical data structures in a declarative way.
//                """
//            }
//            p {
//                +"""
//                    Simply say, type-safe builders is a combination of some Kotlin features and syntax sugars which
//                    allows you to write code as a HTML or JSX like declarative way which makes our code much more readable.
//                """
//            }
//            p {
//                +"""
//                    For example, creating a simple login dialog is looks like this:
//                """
//            }
//            codeBlock(
//                """
//Modal("Login") {
//    TextField("Email")
//    TextField("Password") { password = true }
//
//    HBox {
//        Button("Cancel")
//        Button("Login")
//    }
//}
//                """
//            )
//            p {
//                +"""As you can see, type-safe builders allows us to embed components inside each other by a more visual,
//                    declarative way which is much more ideal to describe component trees.
//                """
//            }
//
//            title("Hey, but these are just functions!")
//            p {
//                +"""
//                    You are right. Every component in Visage is basically a simple function with an initialisation lambda
//                    as the last argument of the function, but thanks to Kotlin, these lambdas can be written outside of
//                    the method's parentheses and if the method does not have any other parameters, than the parentheses can
//                    be omitted.
//                """
//            }
//            p {
//                +"""
//                    This means you can do anything inside a component's initialization block what you can do in a simple function.
//                    For example, you can use if statements to conditionally render components, you can use loops to render multiple components
//                    and you can also use fields, call other functions, etc... And if you call an another Components builder function, than that component will
//                    be rendered inside the actual component as a child.
//                """
//            }
//            p {
//                +"""
//                    Look at the following example for some inspiration:
//                """
//            }
//            codeBlock(
//                """
//Modal("Example modal") {
//    val listElements = listOf(1, 15, 3, 8 ,5, 2, 7, 6)
//
//    for (e in listElements) {
//        div {
//            if (e % 3 == 0) {
//                b { +"${'$'}e is divisible by 3" }
//            } else {
//                +"${'$'}e is NOT divisible by 3"
//            }
//        }
//    }
//}
//                """
//            )
//
//            title("Specifying Children with Type-safe builders")
//            p {
//                +"""
//                    As you already seen, specifying children with type-safe builders is very easy. All you need to do is
//                    to call the child component's builder function inside the parent component's initialization block.
//                """
//            }
//        }
//    }
//}