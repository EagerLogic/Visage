package visage.rmi.ap

import com.google.auto.service.AutoService
import visage.rmi.VisageController
import org.jetbrains.annotations.Nullable
import java.io.File
import java.io.FileWriter
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("visage.rmi.VisageController")
class VisageControllerProcessor : AbstractProcessor() {

    private var commonPath: String = ""
    private var jsPath: String = ""
    private var jvmPath: String = ""

    override fun process(set: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        println("========== VisageControllerProcessor ==========")

        commonPath = processingEnv.options.get("commonPath") as String
        jvmPath = processingEnv.options.get("jvmPath") as String
        jsPath = processingEnv.options.get("jsPath") as String

        val p = processingEnv.options.get("jvmMainKotlinRoot")
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "########### run proc $p")

        env.getElementsAnnotatedWith(VisageController::class.java)
            .forEach {
                val modelType = getType(it, "model").split(".").last()
                val propsType = getType(it, "props").split(".").last()
                this.processElement(it, modelType, propsType, env)
            }
        return true
    }

    private fun getType(typeElement: Element, type: String): String {
        val annotationMirrors = typeElement.annotationMirrors
        for (annotationMirror in annotationMirrors) {

            // Get the ExecutableElement:AnnotationValue pairs from the annotation element
            val elementValues = annotationMirror.elementValues

            for (entry in elementValues.entries) {
                val key = entry.key.simpleName.toString()
                val value = entry.value.value
                when (key) {
                    type -> {
                        val typeMirror = value as TypeMirror
                        return typeMirror.toString()
                    }
                }
            }
        }

        throw RuntimeException("Can't find $type type on given element!")
    }

    private fun processElement(e: Element, modelType: String, propsType: String, env: RoundEnvironment) {
        if (e.kind != ElementKind.INTERFACE) {
            processingEnv.messager.printMessage(
                Diagnostic.Kind.ERROR,
                "@VisageController annotation can be used only on interfaces"
            )
            return
        }

        val te = e as TypeElement;
        val pkg = processingEnv.elementUtils.getPackageOf(te).toString()
        val className = te.simpleName.toString().removePrefix("I")

        val ctrl = ControllerDef(pkg, className, modelType, propsType)

        te.enclosedElements.forEach {
            if (it.kind == ElementKind.METHOD) {
                val ee = it as ExecutableElement
                val fd = FunctionDef(ee.simpleName.toString())
                ctrl.functions.add(fd)

                ee.parameters.forEach {
                    val ve = it;
                    val pName = ve.simpleName.toString()
                    val pType = ve.asType().toString()
                    val nullable = ve.getAnnotation(Nullable::class.java) != null

                    fd.params.add(ParameterDef(pName, pType, nullable))
                }
            }
        }

        this.createFoldersIfNeeded(ctrl)

        this.generateBeAController(ctrl)
        this.generateBeController(ctrl)
        this.generateFeAController(ctrl)
        this.generateScene(ctrl)
        this.generateFeController(ctrl)
    }

    private fun createFoldersIfNeeded(ctrl: ControllerDef) {
        val subFolders = ctrl.pkg.replace(".", "/")
        val jsFolder = this.jsPath + "/" + subFolders
        val jvmFolder = this.jvmPath + "/" + subFolders

        val jsDir = File(jsFolder);
        if (!jsDir.exists()) {
            jsDir.mkdirs()
        }

        val jvmDir = File(jvmFolder)
        if (!jvmDir.exists()) {
            jvmDir.mkdirs()
        }
    }

    private fun generateBeAController(ctrl: ControllerDef) {
        val className = "A" + ctrl.name + "ControllerBe"

        val sb = StringBuilder()
        sb.append("""
package ${ctrl.pkg}

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import visage.rmi.*

abstract class A${ctrl.name}ControllerBe {

    protected lateinit var props: ${ctrl.propsType}
        private set

    protected lateinit var model: ${ctrl.modelType}
        private set

    private lateinit var _request: VisageActionRequest
    private lateinit var _response: VisageActionResponse

    protected val request: VisageActionRequest by lazy {
        _request
    }

    protected val response: VisageActionResponse by lazy {
        _response
    }

    fun init(propsStr: String, modelStr: String, request: VisageActionRequest, response: VisageActionResponse) {
        props = Json.parse(${ctrl.propsType}.serializer(), propsStr)
        model = Json.parse(${ctrl.modelType}.serializer(), modelStr)
        _request = request
        _response = response
    }

        """.trim())
        sb.append("\n")
        sb.append("\n")

        ctrl.functions.forEach {
            val fd = it

            sb.append("    @VisageRemoteAction\n")
            sb.append("    abstract fun ").append(fd.name).append("(")

            fd.params.forEachIndexed { index, pd ->
                if (index != 0) {
                    sb.append(", ")
                }

                sb.append(pd.name).append(": ").append(replaceJavaType(pd.type, pd.isNullable))
            }

            sb.append("): ").append(replaceJavaType(ctrl.modelType)).append("\n")

            sb.append("\n")

            sb.append("    fun _call_${fd.name}(params: String, userLevel: Int?): String {\n")
            sb.append("        @Serializable\n")
            sb.append("        class CallParamsWrapper(")
            it.params.forEachIndexed() { pidx, param ->
                sb.append("val ${param.name}: ${replaceJavaType(param.type, param.isNullable)}")

                if (pidx != fd.params.size - 1) {
                    sb.append(",")
                }
            }
            sb.append(")\n")

            sb.append("        val callParamsParsed = Json.parse(CallParamsWrapper.serializer(), params)\n")
            sb.append("        val method = this::class.java.declaredMethods.firstOrNull() {\n")
            sb.append("            it.name == \"${fd.name}\"\n")
            sb.append("        }\n")
            sb.append("        val authAnnotation = method!!.getAnnotation(Authenticated::class.java)\n")
            sb.append("        if (authAnnotation != null) {\n")
            sb.append("            if (userLevel == null) {\n")
            sb.append("                throw AuthenticationFailedException()\n")
            sb.append("            }\n")
            sb.append("            if (authAnnotation.minLevel > userLevel) {\n")
            sb.append("                throw AuthorizationFailedException()\n")
            sb.append("            }\n")
            sb.append("        }\n")

            sb.append("        val res = ${fd.name}(")
            it.params.forEachIndexed() { pidx, param ->
                sb.append("${param.name} = callParamsParsed.${param.name}")
                if (pidx != fd.params.size - 1) {
                    sb.append(",")
                }
            }
            sb.append(")\n")
            sb.append("        return Json.stringify(${ctrl.modelType}.serializer(), res)\n")
            sb.append("    }\n")
            sb.append("\n")
        }

        sb.append("}\n")

        val fileUrl = this.getClassUrl(this.jvmPath, ctrl.pkg, className)
        writeFile(fileUrl, sb.toString())
    }

    private fun generateBeController(ctrl: ControllerDef) {
        val className = ctrl.name + "ControllerBe"
        val fileUrl = this.getClassUrl(this.jvmPath, ctrl.pkg, className)

        if (File(fileUrl).exists()) return

        val sb = StringBuilder()
        sb.append("package ").append(ctrl.pkg).append("\n")
        sb.append("\n")

        sb.append("class ${ctrl.name}ControllerBe: A${ctrl.name}ControllerBe() {\n")

        ctrl.functions.forEach {
            val fd = it

            sb.append("    override fun ").append(fd.name).append("(")

            fd.params.forEachIndexed { index, pd ->
                if (index != 0) {
                    sb.append(", ")
                }

                sb.append(pd.name).append(": ").append(replaceJavaType(pd.type, pd.isNullable))
            }

            sb.append(") : ${ctrl.modelType}").append(" {\n")
                .append("        TODO(\"not implemented\")\n")
                .append("    }\n")
        }

        sb.append("}\n\n")

        writeFile(fileUrl, sb.toString())
    }

    private fun generateFeAController(ctrl: ControllerDef) {
        val className = "A" + ctrl.name + "ControllerFe"

        val sb = StringBuilder()
        sb.append("package ").append(ctrl.pkg).append("\n")
        sb.append("\n")
        sb.append("import kotlinx.serialization.Serializable\n")
        sb.append("import kotlinx.serialization.json.Json\n")
        sb.append("import visage.core.*\n")
        sb.append("import visage.rmi.*\n")
        sb.append("import kotlin.coroutines.CoroutineContext\n")
        sb.append("import kotlin.coroutines.suspendCoroutine\n")
        sb.append("import kotlinx.coroutines.CoroutineScope\n")
        sb.append("import kotlinx.coroutines.Dispatchers\n")
        sb.append("import kotlinx.coroutines.Job\n")
        sb.append("import kotlinx.coroutines.launch\n")

        sb.append("\n")

        sb.append("abstract class ").append(className).append("(props: ${ctrl.propsType}, scene: M${ctrl.name}Scene)\n")
                .append(" : AController<${ctrl.propsType}, ${ctrl.modelType}, ${ctrl.name}SceneState>(props, scene)")
                .append(" {\n")

        sb.append("    val backend = ${ctrl.name}ControllerBeProxy(this)\n")
        sb.append("    val coroutineScope = DefaultControllerScope()\n\n")

        sb.append("    fun launchOnDefaultControllerScope(block: suspend CoroutineScope.() -> Unit) {\n")
        sb.append("        this.coroutineScope.launch(block = block)\n")
        sb.append("    }\n\n")

        ctrl.functions.forEach {
            val fd = it

            sb.append("    fun ").append("call_${fd.name}").append("(")

            fd.params.forEachIndexed { index, pd ->
                if (index != 0) {
                    sb.append(", ")
                }

                sb.append(pd.name).append(": ").append(replaceJavaType(pd.type, pd.isNullable))
            }

            sb.append(") {\n")

            sb.append("        launchOnDefaultControllerScope {\n")

            sb.append("            render(backend.${fd.name}").append("(")
            fd.params.forEachIndexed { index, pd ->
                if (index != 0) {
                    sb.append(", ")
                }

                sb.append(pd.name)
            }

            sb.append("))\n")
            sb.append("        }\n")
            sb.append("    }\n\n")
        }

        sb.append("    class DefaultControllerScope : CoroutineScope {\n")
        sb.append("        private val job = Job()\n\n")
        sb.append("        override val coroutineContext: CoroutineContext\n")
        sb.append("            get() = Dispatchers.Main + job\n")
        sb.append("    }\n\n")

        sb.append("}\n\n")

        sb.append(generateBackendProxy(ctrl))

        val fileUrl = this.getClassUrl(this.jsPath, ctrl.pkg, className)
        writeFile(fileUrl, sb.toString())
    }

    private fun generateBackendProxy(ctrl: ControllerDef) : String {
        val className = ctrl.name + "ControllerBeProxy(private val controller: A${ctrl.name}ControllerFe)"

        val sb = StringBuilder()

        sb.append("class ").append(className).append(" {\n")

        ctrl.functions.forEach {
            val fd = it

            sb.append("    suspend fun ").append(fd.name).append("(")

            fd.params.forEachIndexed { index, pd ->
                if (index != 0) {
                    sb.append(", ")
                }

                sb.append(pd.name).append(": ").append(replaceJavaType(pd.type, pd.isNullable))
            }

            sb.append(") : ${ctrl.modelType} {\n")
            sb.append("        @Serializable\n")
            sb.append("        class ParamsWrapper(")

            fd.params.forEachIndexed { index, pd ->
                if (index != 0) {
                    sb.append(",")
                }

                sb.append("val ${pd.name}: ${replaceJavaType(pd.type, pd.isNullable)}")
            }
            sb.append(")\n")

            sb.append("        val params: String = Json.stringify(ParamsWrapper.serializer(), ParamsWrapper(")
            fd.params.forEachIndexed { index, pd ->
                if (index != 0) {
                    sb.append(",")
                }

                sb.append(pd.name)
            }
            sb.append("))")

            sb.append("\n")

            sb.append("        val p = RmiParams(\n")
            sb.append("            controllerName = \"${ctrl.pkg}.${ctrl.name}ControllerBe\",\n")
            sb.append("            aControllerName = \"${ctrl.pkg}.A${ctrl.name}ControllerBe\",\n")
            sb.append("            methodName = \"${fd.name}\",\n")
            sb.append("            methodParams = params,\n")
            sb.append("            props = Json.stringify(${replaceJavaType(ctrl.propsType)}.serializer(), controller.props),\n")
            sb.append("            currentModel = Json.stringify(${replaceJavaType(ctrl.modelType)}.serializer(), controller.model)\n")
            sb.append("        )\n")

            sb.append("        return suspendCoroutine { res ->\n")
            sb.append("            val promise = RmiClient.call(p)\n")
            sb.append("            RmiClient.handleResult(controller.model, res, promise, ${ctrl.modelType}.serializer())\n")
            sb.append("        }\n")
            sb.append("    }\n")
        }

        sb.append("}\n")

        return sb.toString()
    }

    private fun generateScene(ctrl: ControllerDef) {
        val className = "M" + ctrl.name + "Scene"
        val sceneStateClassName = ctrl.name + "SceneState"
        val fileUrl = this.getClassUrl(this.jsPath, ctrl.pkg, ctrl.name + "Scene")

        if (File(fileUrl).exists()) return

        val sb = StringBuilder()
        sb.append("package ").append(ctrl.pkg).append("\n")
        sb.append("\n")
        sb.append("import visage.core.AComponent\n")
        sb.append("import visage.core.Components\n")
        sb.append("import visage.core.AScene\n")
        sb.append("\n")

        sb.append("class ").append(className).append("(props: ${ctrl.propsType})")
            .append(" : AScene<${ctrl.propsType}, ${ctrl.modelType}, $sceneStateClassName, ${ctrl.name + "ControllerFe"}>(props)")
            .append(" {\n\n")

        sb.append("    override fun createController() : ").append(ctrl.name + "ControllerFe").append(" {\n")
            .append("        return ").append(ctrl.name + "ControllerFe").append("(props, this)\n")
            .append("    }\n\n")

        sb.append("    override fun initState() : ").append(sceneStateClassName).append(" {\n")
            .append("        return $sceneStateClassName()\n")
            .append("    }\n\n")

        sb.append("    override fun Components.render(children: List<AComponent<*>>) {\n")
            .append("        TODO(\"not implemented\")\n")
            .append("    }\n")

        sb.append("}\n\n")

        sb.append("class $sceneStateClassName {\n\n}\n\n")

        sb.append("fun Components.${ctrl.name}Scene(props: ${ctrl.propsType}) = this.registerComponent(${className}(props), {})\n")

        writeFile(fileUrl, sb.toString())
    }

    private fun generateFeController(ctrl: ControllerDef) {
        val className = ctrl.name + "ControllerFe"
        val fileUrl = this.getClassUrl(this.jsPath, ctrl.pkg, className)

        if (File(fileUrl).exists()) return

        val sb = StringBuilder()
        sb.append("package ").append(ctrl.pkg).append("\n")
        sb.append("\n")
        sb.append("\n")

        sb.append("class ").append(className).append("(props: ${ctrl.propsType}, scene: M${ctrl.name}Scene)")
            .append(" : A$className(props, scene)")
            .append(" {\n\n")

        sb.append("    override fun createInitialModel() : ").append(ctrl.modelType).append(" {\n")
            .append("        TODO(\"not implemented\")\n")
            .append("    }\n\n")

        sb.append("    override fun init() {}\n\n")

        sb.append("\n")

        sb.append("}\n\n")

        writeFile(fileUrl, sb.toString())
    }

    private fun writeFile(url: String, content: String): Unit {
        FileWriter(url).use {
            it.write(content)
        }
    }

    private fun getClassUrl(basePath: String, pkg: String, name: String): String {
        var res = basePath;
        if (!res.endsWith("/")) {
            res += "/"
        }
        res += pkg.replace(".", "/")
        if (!res.endsWith("/")) {
            res += "/"
        }
        res += name + ".kt"

        return res
    }

    private fun replaceJavaType(jt: String, isNullable: Boolean = false): String {

        var jType = jt
        var isArray = false;
        if (jType.endsWith("[]")) {
            isArray = true
            jType = jType.substring(0, jType.length - 2)
        }

        var res = jType
        res = res.replace("java.lang.String", "kotlin.String")

        res = res.replace("byte", "kotlin.Byte")
        res = res.replace("short", "kotlin.Short")
        res = res.replace("int", "kotlin.Int")
        res = res.replace("long", "kotlin.Long")
        res = res.replace("float", "kotlin.Float")
        res = res.replace("double", "kotlin.Double")
        res = res.replace("char", "kotlin.Char")
        res = res.replace("boolean", "kotlin.Boolean")

        res = res.replace("java.lang.Byte", "kotlin.Byte")
        res = res.replace("java.lang.Short", "kotlin.Short")
        res = res.replace("java.lang.Integer", "kotlin.Int")
        res = res.replace("java.lang.Long", "kotlin.Long")
        res = res.replace("java.lang.Float", "kotlin.Float")
        res = res.replace("java.lang.Double", "kotlin.Double")
        res = res.replace("java.lang.Character", "kotlin.Char")
        res = res.replace("java.lang.Boolean", "kotlin.Boolean")

        res = res.replace("java.util.List", "kotlin.collections.List")
        res = res.replace("java.util.Set", "kotlin.collections.Set")
        res = res.replace("java.util.Map", "kotlin.collections.Map")
        res = res.replace("java.util.Collection", "kotlin.collections.Collection")

        if (isNullable) {
            res += "?"
        }

        if (isArray) {
            res = "kotlin.Array<" + res + ">"
        }

        return res
    }


}

internal data class ControllerDef(
    val pkg: String,
    val name: String,
    val modelType: String,
    val propsType: String
) {
    public val functions: MutableList<FunctionDef> = ArrayList()
}

internal data class FunctionDef(
    val name: String
) {
    val params: MutableList<ParameterDef> = ArrayList()
}

internal data class ParameterDef(
    val name: String,
    val type: String,
    val isNullable: Boolean
)
