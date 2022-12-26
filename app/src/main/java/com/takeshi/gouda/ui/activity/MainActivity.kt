package com.takeshi.gouda.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.takeshi.gouda.R
import com.takeshi.gouda.model.User
import com.takeshi.gouda.databinding.ActivityMainBinding
import com.takeshi.gouda.adapter.UserAdapter
import com.takeshi.gouda.ui.viewmodel.MainViewModel
import com.takeshi.gouda.ui.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelSearch: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      set action bar title
        supportActionBar?.title = resources.getString(R.string.title)

//      binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModelSearch = ViewModelProvider(this)[SearchViewModel::class.java]

//      flexible recyclerview
        binding.rvUser.setHasFixedSize(true)
        showRecyclerList()
        dataSearch(query = String())
        searchUser()
    }

    private fun dataSearch(query: String) {
        binding.pgUser.visibility = View.VISIBLE
        viewModelSearch.searchUser(query)
        viewModelSearch.observeUserLiveData().observe(this){ dataList ->
            binding.pgUser.visibility = View.GONE
            val userListAdapter = UserAdapter(dataList)
            binding.rvUser.adapter = userListAdapter
            binding.rvUser.layoutManager = LinearLayoutManager(this@MainActivity)

            userListAdapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    showSelectedUser(data)
                }

            })
        }
    }

    private fun searchUser() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    dataSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                showRecyclerList()
                return false
            }

        })
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}