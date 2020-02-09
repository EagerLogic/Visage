package visage.rmi

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify
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
            } catch (ex: AuthenticationFailedException) {
                rmiRes = RmiResult(ERmiResultType.AuthenticationFailed, "")
                status = 200
            } catch (ex: AuthorizationFailedException) {
                rmiRes = RmiResult(ERmiResultType.AuthorizationFailed, "")
                status = 200
            } catch (ex: RmiException) {
                rmiRes = RmiResult(ERmiResultType.AuthorizationFailed, ex.message!!)
                status = 200
            } catch (ex: RedirectException) {
                rmiRes = RmiResult(ERmiResultType.AuthorizationFailed, Json.stringify(RmiRedirectResult.serializer(), RmiRedirectResult(ex.url, ex.hard)))
                status = 200
            } catch (ex: Exception) {
                rmiRes = RmiResult(ERmiResultType.AuthorizationFailed, ex.message!!)
                status = 500
            }
            resp.sendResponse(status, Json.stringify(RmiResult.serializer(), rmiRes!!))

        }

    }

}

interface IVisageRmiUserLevelProvider {

    fun getUserLevel(req: IVisageRequest): Int?

}
