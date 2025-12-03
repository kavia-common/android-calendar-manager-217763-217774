package org.example.app.ui.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * PUBLIC_INTERFACE
 * Pager adapter for Month, Week, and Day fragments.
 */
class CalendarPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MonthFragment()
            1 -> WeekFragment()
            else -> DayFragment()
        }
    }
}
