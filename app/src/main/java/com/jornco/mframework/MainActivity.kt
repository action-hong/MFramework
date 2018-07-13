package com.jornco.mframework

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jornco.mframework.ui.activities.TestActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, TestActivity::class.java))
    }
}
