package jermaine.technews.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Automatically initializes ViewDataBinding class and ViewModel class for your activity.
 *
 * Note: When extending this class, you don't need to explicitly call `setContentView`
 * inside `onCreate` in your activity.
 */
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    @set:Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: B
    lateinit var viewModel: VM

    @get:LayoutRes
    abstract val layoutId: Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            layoutId
        )
        binding.lifecycleOwner = this

        // Gets the class type passed in VM parameter.
        // https://stackoverflow.com/a/52073780/5285687
        val viewModelClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }
}