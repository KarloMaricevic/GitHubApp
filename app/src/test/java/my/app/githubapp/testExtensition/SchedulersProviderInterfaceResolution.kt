package my.app.githubapp.testExtensition

import io.mockk.every
import io.mockk.mockkClass
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.utils.schedulers.SchedulersProvider
import my.app.githubapp.utils.schedulers.SchedulersProviderInterface
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class SchedulersProviderInterfaceResolution : ParameterResolver {
    override fun supportsParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Boolean {
        if (parameterContext!!.parameter.parameterizedType == SchedulersProviderInterface::class.java) {
            return true
        }
        return false
    }

    override fun resolveParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Any {
        val mockedProvider = mockkClass(SchedulersProvider::class)
        every { mockedProvider.getMainThread() }.returns(Schedulers.trampoline())
        every { mockedProvider.getNetworkThread() }.returns(Schedulers.trampoline())
        return mockedProvider

    }
}