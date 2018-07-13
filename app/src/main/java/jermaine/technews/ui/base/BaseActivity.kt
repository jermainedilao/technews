package jermaine.technews.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jermaine.technews.application.App


abstract class BaseActivity : AppCompatActivity() {
    fun getComponent() = (application as App).component

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}