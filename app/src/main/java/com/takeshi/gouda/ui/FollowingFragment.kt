package com.takeshi.gouda.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.takeshi.gouda.databinding.FragmentFollowingBinding
import com.takeshi.gouda.model.User
import com.takeshi.gouda.ui.adapter.UserAdapter
import com.takeshi.gouda.ui.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvFollow.setHasFixedSize(true)
        binding.pgUser.visibility = View.VISIBLE
        activity?.intent?.getStringExtra(DETAIL_KEY)?.let { viewModel.getFollowing(it) }
        viewModel.observeUserLiveData().observe(viewLifecycleOwner) {  userList ->
            binding.pgUser.visibility = View.GONE
            val listUserAdapter = UserAdapter(userList)
            binding.rvFollow.adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    showSelectedUser(data)
                }

            })
        }
        binding.rvFollow.layoutManager = LinearLayoutManager(context)
    }

    private fun showSelectedUser(data: User) {
        Toast.makeText(context, "You choose " + data.login, Toast.LENGTH_SHORT).show()
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL_KEY, data.login)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DETAIL_KEY = "detail_key"
    }
}