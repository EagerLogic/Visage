package visage.rmi

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions

class VisageRmiHandler private constructor() {

    companion object {

        @UnstableDefault
        fun callRmi(req: IVisageRequest, resp: IVisageResponse, userLevelProvider: IVisageRmiUserLevelProvider?) {
            val inputJson = req.readContent()

            val params: RmiParams = Json.parse(RmiParams.serializer(), inputJson)

            val aCtrlName = params.aControllerName
            val aClass = Class.forName(aCtrlName).kotlin
            aClass.memberFunctions.forEach { memberFunction ->
                if (!memberFunction.isAbstract) return@forEach

                if (
                    (memberFunction.name == params.methodName) &&
                    (memberFunction.findAnnotation<VisageRemoteAction>() == null)
                ) {
                    resp.sendResponse(400, "")
                    return
                }
            }

            val ctrlName = params.controllerName
            val ctrlClass = Class.forName(ctrlName).kotlin
            val constr = ctrlClass.constructors.first()

            val ctrlInstance = constr.call()
            val initMethod = ctrlClass.members.find { method ->
                method.name == "init"
            }
            initMethod!!.call(
                ctrlInstance,
                params.props,
                params.currentModel,
                VisageActionRequest(req),
                VisageActionResponse(resp)
            )

            val callerMethod = ctrlClass.members.find {
                it.name == "_call_" + params.methodName
            }

            val userLevel: Int? = userLevelProvider?.getUserLevel(req)

            var rmiRes: RmiResult? = null
            var status: Int = 0
            try {
                val res = callerMethod!!.call(ctrlInstance, params.methodParams, userLevel)
                rmiRes = RmiResult(ERmiResultType.Success, res.toString())
                status = 200
            } catch (ex: InvocationTargetException) {
                when (ex.targetException) {
                    is AuthenticationFailedException -> {
                        rmiRes = RmiResult(ERmiResultType.AuthenticationFailed, "")
                        status = 200
                    }
                    is AuthorizationFailedException -> {
                        rmiRes = RmiResult(ERmiResultType.AuthorizationFailed, "")
                        status = 200
                    }
                    is RmiException -> {
                        rmiRes = RmiResult(ERmiResultType.RmiException, ex.message!!)
                        status = 200
                    }
                    is RedirectException -> {
                        rmiRes = RmiResult(ERmiResultType.Redirect, Json.stringify(RmiRedirectResult.serializer(), RmiRedirectResult((ex.targetException as RedirectException).url, (ex.targetException as RedirectException).hard)))
                        status = 200
                    }
                    else -> {
                        throw ex
                    }
                }
            }
            resp.sendResponse(status, Json.stringify(RmiResult.serializer(), rmiRes!!))

        }

    }

}

interface IVisageRmiUserLevelProvider {

    fun getUserLevel(req: IVisageRequest): Int?

}
