package com.example.localdogs

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.amplifyframework.auth.result.AuthSignInResult
import com.example.localdogs.data.User
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
        val newUser = User("Trevor", "Lockhart", "anotherone@anotherone.com", "01/01/2001")

        Authentication.getInstance(appContext).registerUser("anotherone@anotherone.com", "remember!@#$1234", newUser, {success -> run {
            Log.i("Auth", "user registered and confirmed")
            synchronized(obj){
                obj.notify()
            }
        }}, {error -> run {
            Log.e("Auth", "User failed to register", error)
            synchronized(obj){
                obj.notify()
            }
        }} )
        synchronized(obj){
            obj.wait()
        }
    }
}