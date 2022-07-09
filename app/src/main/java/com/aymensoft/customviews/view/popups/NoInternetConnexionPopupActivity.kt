package com.aymensoft.customviews.view.popups

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.aymensoft.customviews.databinding.ActivityNoInternetConnexionPopupBinding

/**
 * alert user when no internet connexion
 * @author Aymen Masmoudi
 * */
class NoInternetConnexionPopupActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNoInternetConnexionPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInternetConnexionPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSettings.setOnClickListener {
            //start internet settings activity
            startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            finish()
        }

    }

}