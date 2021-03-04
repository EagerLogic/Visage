# Visage
Visage is a WEB frontend framework for Kotlin. The main goal of this project is to create a framework which is designed from the ground up to work perfectly with Kotlin and utilise the language features to make frontend developers life much easier covering most of the aspects of frontend development.

## Documentation
The documentation can be found on the webpage:
https://visageui.appspot.com

 At this time it's in a very early stage and under active development. Stay tuned to see whats coming.

## Modules
Visage contains different modules not just the UI Part. At this time, Visage is an all in one library which means all the following modules are packed in the Visage library, but you don't need to use all of them. In a later time it may be sliced to different standalone modules but not now.

### Core (React like UI API)
The core of Visage is a React like UI API which enables you to create stunning frontends a declarative way using Kotlin's typesafe builders. Just like in React you can put together default HTML elements but you can also define your own components to split your code. Kotlin as a language has much more potential than which can be achieved with simple JSX or with a React wrapper. The goal of this module is to keep what is outstanding in React (declarative UI development and virtual DOM) but enhance it and fix some problems with React which can be done better using Kotlin.

### VMVC
VMVC is consist to (ViewModel-View-Controller) a mixed and modified version of MVC, MVP and MVVM. This module can be used to separate the view from the data which is rendered and the business logic between the two. Nothing really special, just some base classes and best practices which makes your code cleaner. This is an optional module. You don't need to use this module or follow the guidelines. You can use your own structure if you want.

### Router
The router module makes it easy to define what (Component / Scene) needs to be rendered on specific URL-s. Useful for Single Page Applications. Similar to React Router. This is an optional module. You don't need to use it, you can use your own navigation / routing code if you want.

### CSS Styling
This module is responsible to easily create CSS classes in Kotlin code, next to your components, or wherever you want. Similar to Typestyle for React/Typescript. This is an optional module, you don't need to use it. You can define your CSS howewer you want.

### RMI
This module helps a lot to communicate with any JVM backend. The goal of this project is to create client-server communication as painful as possible, using annotation processor generated shared controllers, which methods can be called from the client as a single method call. You will learn more from the concept of sharing controller and model between frontend and backend as a stateless way but it is similar what JSF do but in a stateless and typesafe way. This is also an optional module, you don't need to use it, you can use your own method to communicate with any backend using fetch or whatever.

### Validation (Coming soon)
A simple but powerful common validation library to eliminate the need to rewrite the validation code on both client and server side. This is also an optional modul.

### Components (Coming soon)
This module contains basic Visage components (like Button, TextField, RadioButton, etc...) and layouts (like HBox, VBox, Grid, etc...) which can be used out of the box to create stunning UI-s. This is an optional module, you don't need to use any of this components, you can use your own or any other third party Visage components.




