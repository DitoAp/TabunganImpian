package com.example.tabunganimpian

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Berlangsung" else "Selesai"
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    it.view.setBackgroundResource(R.drawable.tab_selected)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let {
                    it.view.setBackgroundResource(R.drawable.tab_unselected)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // No action needed
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_NEW_PAGE && resultCode == RESULT_OK) {
//            val title = data?.getStringExtra("title")
//            val target = data?.getStringExtra("target")
//            val saving = data?.getStringExtra("saving")
//            val tipe = data?.getStringExtra("tipe")
//            val imageUri = data?.getStringExtra("imageUri")
//
//            // Use ViewPager2 to get the current fragment
//            val fragment = supportFragmentManager.findFragmentByTag("f0") as? FirstPageFragment
//            fragment?.updateContent(title, target, saving, tipe, imageUri)
//        }
    }

    companion object {
        const val REQUEST_CODE_NEW_PAGE = 1
    }
}
