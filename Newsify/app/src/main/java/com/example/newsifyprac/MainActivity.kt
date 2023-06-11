package com.example.newsifyprac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsifyprac.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

val newsDataManager = NewsDataManager()
var data_real = arrayListOf<NewsData>()

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myViewModel:MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()


    }

    fun initLayout(){
        newsDataManager.initNewsData()
        data_real = newsDataManager.getNews()

        myViewModel.setLiveData(0)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_news -> {
                    loadFragment(NewsFragment())
                    true
                }
                R.id.navigation_mypage -> {
                    loadFragment(MypageFragment())
                    true
                }
                R.id.navigation_settings -> {
                    loadFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }

        binding.home.setOnClickListener {
            val fragment = supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)
            val newsFragment = NewsFragment()
            fragment.replace(R.id.frameLayout, newsFragment)
            fragment.commit()
            bottomNavigationView.selectedItemId = R.id.navigation_news
        }

        // To display initially selected fragment
        loadFragment(NewsFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
}