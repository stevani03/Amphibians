package id.febimaharani.amphibians

import android.app.Application
import id.febimaharani.amphibians.data.AppContainer
import id.febimaharani.amphibians.data.DefaultAppContainer

class AmphibiansApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
