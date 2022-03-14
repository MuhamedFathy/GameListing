package com.github.gamelisting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gamelisting.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(ActivityMainBinding.inflate(layoutInflater).root)
  }
}
