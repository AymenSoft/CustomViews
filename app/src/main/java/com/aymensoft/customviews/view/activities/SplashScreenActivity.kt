package com.aymensoft.customviews.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aymensoft.customviews.R
import com.aymensoft.customviews.databinding.ActivitySplashScreenBinding
import com.aymensoft.customviews.view.popups.NoInternetConnexionPopupActivity
import com.aymensoft.customviews.utils.checkForInternet
import com.aymensoft.customviews.utils.checkForServerConnection

/**
 * startup activity
 * start HomeScreenActivity
 * @author Aymen Masmoudi
 * */
@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private var isPaused:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //extend interface layout to full screen
        binding.rlSplash.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

    }

    //test internet connection
    fun isConnected(): Boolean {
        if (checkForInternet(this)) {
            if (checkForServerConnection()){ // Successful response.
                return true
            } else {//server offline
                serverConnexionProblems(resources.getString(R.string.splash_server_down))
            }
        } else {//no internet
            serverConnexionProblems(resources.getString(R.string.splash_no_internet))
        }
        return false
    }

    //enable server connexion errors
    private fun serverConnexionProblems(message:String){
        runOnUiThread {
            if (message == resources.getString(R.string.splash_no_internet)){
                startActivity(Intent(this@SplashScreenActivity, NoInternetConnexionPopupActivity::class.java))
            }else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    //start test every 5 seconds
    override fun onResume() {
        super.onResume()
        isPaused = false
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    do {
                        // Thread will sleep for 2 seconds
                        sleep(2000)
                    }while (!isPaused && !isConnected())
                    if (!isPaused && isConnected()){
                        startActivity(Intent(this@SplashScreenActivity, HomeScreenActivity::class.java))
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        // start thread
        background.start()
    }

    //pause test when activity is paused
    override fun onPause() {
        isPaused = true
        super.onPause()
    }

}
