package com.ozanarik.MvvmNewsApp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments.BusinessFragment
import com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments.EntertainmentFragment
import com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments.GeneralFragment
import com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments.HealthFragment
import com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments.ScienceFragment
import com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments.SportsFragment
import com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments.TechnologyFragment
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.NEWS_CATEGORY_LIST

class ViewPagerAdapter(fragmentManager: FragmentManager,lifeCycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifeCycle) {


    val tabTitleList = NEWS_CATEGORY_LIST

    override fun getItemCount(): Int {

        return tabTitleList.size
    }

    override fun createFragment(position: Int): Fragment {

        when(position){

            0->{return GeneralFragment()
            }
            1->{return BusinessFragment()
            }
            2->{return EntertainmentFragment()
            }
            3->{return HealthFragment()
            }
            4->{return ScienceFragment()
            }
            5->{return SportsFragment()
            }
            6->{return TechnologyFragment()
            }
        }

        return GeneralFragment()
    }


}