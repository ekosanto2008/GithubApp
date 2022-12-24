package com.takeshi.gouda.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.takeshi.gouda.R
import com.takeshi.gouda.model.User
import com.takeshi.gouda.databinding.ActivityMainBinding
import com.takeshi.gouda.ui.adapter.UserAdapter
import com.takeshi.gouda.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      set action bar title
        supportActionBar?.title = resources.getString(R.string.title)

//      binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

//      flexible recyclerview
        binding.rvUser.setHasFixedSize(true)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.pgUser.visibility = View.VISIBLE
        viewModel.getUsers()
        viewModel.observeUserLiveData().observe(this) { userList ->
            binding.pgUser.visibility = View.GONE
            val listUserAdapter = UserAdapter(userList)
            binding.rvUser.adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    showSelectedUser(data)
                }

            })
        }
        binding.rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun showSelectedUser(data: User) {
        Toast.makeText(this, "You Choose " + data.login, Toast.LENGTH_SHORT).show()
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL_KEY, data.login)
        startActivity(intent)
    }
}