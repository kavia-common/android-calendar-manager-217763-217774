package org.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.example.app.ui.calendar.CalendarPagerAdapter

class MainActivity : AppCompatActivity() {

    // PUBLIC_INTERFACE
    override fun onCreate(savedInstanceState: Bundle?) {
        /** Initialize the main UI with a ViewPager and BottomNavigation to switch between Month/Week/Day.
         * A FloatingActionButton opens the EventEditFragment to create a new event.
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize networking client
        org.example.app.data.ApiClient.init(this)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)

        viewPager.adapter = CalendarPagerAdapter(this)
        viewPager.isUserInputEnabled = true

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_month -> viewPager.currentItem = 0
                R.id.menu_week -> viewPager.currentItem = 1
                R.id.menu_day -> viewPager.currentItem = 2
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNav.menu.findItem(R.id.menu_month).isChecked = true
                    1 -> bottomNav.menu.findItem(R.id.menu_week).isChecked = true
                    2 -> bottomNav.menu.findItem(R.id.menu_day).isChecked = true
                }
            }
        })

        fab.setOnClickListener {
            // Launch event edit fragment
            val dialog = org.example.app.ui.events.EventEditFragment.newInstance(null)
            dialog.show(supportFragmentManager, "EventEdit")
        }
    }
}
