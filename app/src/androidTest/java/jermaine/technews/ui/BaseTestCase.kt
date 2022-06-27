package jermaine.technews.ui

import android.Manifest
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.UiDevice
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import jermaine.data.db.AppDatabase
import jermaine.technews.application.App
import org.junit.Before
import org.junit.Rule

abstract class BaseTestCase : TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple(
        customize = {
            // storage support for API 30+
            if (isAndroidRuntime) {
                UiDevice
                    .getInstance(instrumentation)
                    .executeShellCommand(
                        "appops set --uid ${
                            InstrumentationRegistry
                                .getInstrumentation()
                                .targetContext.packageName
                        } MANAGE_EXTERNAL_STORAGE allow"
                    )
            }
        }
    )
) {
    // storage support for Android API 29-
    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @Before
    open fun setUp() {
        ApplicationProvider
            .getApplicationContext<App>()
            .deleteDatabase(AppDatabase.DATABASE_NAME)
    }
}