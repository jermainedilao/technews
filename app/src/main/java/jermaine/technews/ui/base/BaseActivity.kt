package jermaine.technews.ui.base

import android.support.v7.app.AppCompatActivity
import jermaine.technews.application.App


abstract class BaseActivity : AppCompatActivity() {
    fun getComponent() = (application as App).component
}