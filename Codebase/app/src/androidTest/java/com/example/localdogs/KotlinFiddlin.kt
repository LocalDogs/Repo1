package com.example.localdogs

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.amplifyframework.auth.result.AuthSignInResult
import com.example.localdogs.data.awsinterface.AmplifyHub
import com.example.localdogs.data.awsinterface.Authentication
import okhttp3.internal.notifyAll
import org.junit.Test

class KotlinFiddlin {

    @Test
    fun tester(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val obj = Object()
        AmplifyHub.launchAmplify(appContext)
        Authentication.getInstance(appContext).signInUser("rememberthis@remember.com", "remember!@#$1234", { success ->
            run {
                Log.i("Auth", success.toString())

                synchronized(obj) {
                    obj.notifyAll()
                }

            }
        }, { failure ->
            run {
            Log.e("Auth", failure.recoverySuggestion)
            synchronized(obj) {
                obj.notifyAll()
            }
        }})
        synchronized(obj){
            obj.wait()
        }
    }
}