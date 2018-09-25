package com.example.android.quakereport.base

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.rule.IntentsTestRule
import com.example.android.quakereport.EarthquakeActivity
import com.example.android.quakereport.QueryUtils.okReplayInterceptor
import com.jraska.falcon.FalconSpoonRule
import okreplay.*
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

abstract class BaseTest {

    /* Instantiate an IntentsTestRule object for MainActivity. */
    var intentsRule: IntentsTestRule<EarthquakeActivity> = object : IntentsTestRule<EarthquakeActivity>(EarthquakeActivity::class.java) {
        override fun beforeActivityLaunched() {
            TestUtils.clearSharedPrefs(InstrumentationRegistry.getTargetContext())
            super.beforeActivityLaunched()
        }
    }

    @get:Rule
    val falconSpoonRule = FalconSpoonRule()

    @get:Rule
    var watcher: TestRule = object : TestWatcher() {
        override fun failed(e: Throwable?, description: Description?) {
            // Take a screenshot on test failure for use in Spoon test report
            takeFalconSpoonScreenshot(Regex("[^a-zA-Z0-9_-]").replace(("Error_" + description!!.className + "-" + description!!.methodName), "_"))
        }
    }

    /**
     * configures okreplay http test recorder
     */
    val okReplayConfig: OkReplayConfig = OkReplayConfig.Builder()
            .tapeRoot(AndroidTapeRootSD(AssetManager(InstrumentationRegistry.getInstrumentation().context), this@BaseTest.javaClass.simpleName.toLowerCase(), InstrumentationRegistry.getInstrumentation().targetContext))
            .defaultMode(TapeMode.READ_WRITE)
            .sslEnabled(true)
            .interceptor(okReplayInterceptor)
            .defaultMatchRules( MatchRules.path, MatchRules.method, MatchRules.queryParams, MatchRules.body)
            .build()

    @Rule
    @JvmField
    val testRule: TestRule = RuleChain.outerRule(RecorderRule(okReplayConfig)).around(intentsRule)

    /**
     * Takes a screenshot using Falcon, with Spoon compatibility for use in Spoon test reports
     * Spoon allows you to run parallel tests on connected Android Devices
     */
    fun takeFalconSpoonScreenshot(tag: String){
        // sometimes screenshots are taken while UI elements are still loading, so hopefully this small wait will suffice
       Thread.sleep(200)
        try {
            falconSpoonRule.screenshot(intentsRule.activity, tag)
        } catch(ex: RuntimeException) {
            // Failed to take screenshot.. not a reason to fail a test.
        }
    }
}