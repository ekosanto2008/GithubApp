package com.takeshi.gouda.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.takeshi.gouda.R
import com.takeshi.gouda.adapter.SectionsPagerAdapter
import com.takeshi.gouda.build.UserViewModelFactory
import com.takeshi.gouda.databinding.ActivityDetailBinding
import com.takeshi.gouda.ui.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val injection: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, injection)[DetailViewModel::class.java]

        getData()
        tabView()
    }

    private fun getData() {
        val login = intent.getStringExtra(DETAIL_KEY)
        if (login != null) {
            viewModel.getDetailUser(login).observe(this){ result ->
                if (result != null) {
                    when(result) {
                        is com.takeshi.gouda.Result.Loading -> {
                            showLoading(true)
                        }
                        is com.takeshi.gouda.Result.Success -> {
                            showLoading(false)
                            binding.item1.text = result.data.followers.toString()
                            binding.item2.text = result.data.following.toString()
                            binding.item3.text = result.data.public_repos.toString()
                            Glide.with(this)
                                .load(result.data.avatar_url)
                                .apply(RequestOptions())
                                .into(binding.imgDetail)
                            supportActionBar?.title = result.data.name
                        }
                        is com.takeshi.gouda.Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this, "Error : ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }



    private fun tabView() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pgUser.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
        const val DETAIL_KEY = "detail_key"
    }
}