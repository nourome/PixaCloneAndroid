package noro.me.pixacloneandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import io.realm.Realm
import noro.me.pixacloneandroid.ui.adapters.OnPhotoSelectedInterface
import noro.me.pixacloneandroid.ui.category.CategoryFragment
import noro.me.pixacloneandroid.ui.editor.EditorFragment
import noro.me.pixacloneandroid.ui.imageViewer.ImageViewerFragment
import noro.me.pixacloneandroid.ui.start.StartFragment

class StartActivity : AppCompatActivity() {


    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: ScreenPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)
        viewPager = findViewById(R.id.viewPager)
        pagerAdapter = ScreenPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 1


    }


}

class ScreenPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {

      return when(position) {
            0 ->  EditorFragment.newInstance()
            1 ->  StartFragment.newInstance()
            2 ->  CategoryFragment.newInstance()
            else ->  StartFragment.newInstance()
        }

    }

    override fun getCount(): Int {
        return 3
    }
}
