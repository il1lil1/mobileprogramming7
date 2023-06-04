package com.example.newsifyprac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.newsifyprac.databinding.ActivityMainBinding
val newsDataManager = NewsDataManager()

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

        myViewModel.setLiveData(0)
        val fragment = supportFragmentManager.beginTransaction()
        val newsFragment = NewsFragment()
        fragment.replace(R.id.frameLayout, newsFragment)
        fragment.commit()

        binding.news.setOnClickListener {
            val fragment = supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)
            val newsFragment = NewsFragment()
            fragment.replace(R.id.frameLayout, newsFragment)
            fragment.commit()
        }
        binding.mypage.setOnClickListener {
            val fragment = supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)
            val mypageFragment = MypageFragment()
            fragment.replace(R.id.frameLayout, mypageFragment)
            fragment.commit()
        }
        binding.setting.setOnClickListener {
            val fragment = supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)
            val settingFragment = SettingFragment()
            fragment.replace(R.id.frameLayout, settingFragment)
            fragment.commit()
        }
    }
}