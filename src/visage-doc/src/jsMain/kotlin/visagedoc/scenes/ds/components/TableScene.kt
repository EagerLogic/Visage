//package visagedoc.scenes.ds.components
//
//import visage.core.Components
//import visage.ds.components.*
//import visage.ds.components.page.Page
//import visagedoc.components.*
//
//fun Components.TableScene() = this.registerFunctionalComponent({}) {
//    Page {
//        head("Components / Table")
//        body {
//            DocPage {
//                title("Table")
//                p {
//                    +"""
//                    The table component can be used to show some data in a table like this:
//                """
//                }
//                p {
//                    Table {
//                        columns {
//                            fixedColumn("ID")
//                            fixedColumn("Name")
//                            resp
//                        }
//                        head {
//                            cell("ID")
//                            cell("Name")
//                            cell("Email")
//                        }
//                        body {
//                            row {
//                                cell("1")
//                                cell("John Doe")
//                                cell("johndoe@email.com")
//                            }
//                            row {
//                                cell("2")
//                                cell("Jane Doe")
//                                cell("janedoe@email.com")
//                            }
//                            row {
//                                cell("3")
//                                cell("Anonymus")
//                                cell("whoami@email.com")
//                            }
//                        }
//                    }
//                }
//                codeBlock(
//                    """
//Table {
//    head {
//        cell("ID")
//        cell("Name")
//        cell("Email")
//    }
//    body {
//        row {
//            cell("1")
//            cell("John Doe")
//            cell("johndoe@email.com")
//        }
//        row {
//            cell("2")
//            cell("Jane Doe")
//            cell("janedoe@email.com")
//        }
//        row {
//            cell("3")
//            cell("Anonymus")
//            cell("whoami@email.com")
//        }
//    }
//}
//            """
//                )
//
//                subTitle("Cell properties")
//                properties {
//                    prop(
//                        "text",
//                        "If present, the given text will be rendered inside the cell, otherwise, the children of the cell."
//                    )
//                    prop("width", "The width style property of the cell.")
//                    prop("hAlign", "The horizontal alignment of the cell's content", "EHAlign.Left")
//                    prop("vAlign", "The vertical alignment of the cell's content", "EVAlign.Middle")
//                }
//
//            }
//        }
//
//    }
//}