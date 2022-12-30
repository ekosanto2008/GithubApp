package com.takeshi.gouda.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.takeshi.gouda.databinding.FragmentFollowersBinding
import com.takeshi.gouda.model.User
import com.takeshi.gouda.ui.activity.DetailActivity
import com.takeshi.gouda.adapter.UserAdapter
import com.takeshi.gouda.factory.UserViewModelFactory
import com.takeshi.gouda.ui.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val injection: UserViewModelFactory = UserViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, injection)[FollowersViewModel::class.java]
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        binding.rvFollow.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.intent?.getStringExtra(DETAIL_KEY)?.let {
            viewModel.getFollowers(it).observe(viewLifecycleOwner){ result ->
                if (result != null) {
                    when(result) {
                        is com.takeshi.gouda.Result.Loading -> {
                            showLoading(true)
                        }
                        is com.takeshi.gouda.Result.Success -> {
                            showLoading(false)
                            val listUserAdapter = UserAdapter(result.data)
                            binding.rvFollow.adapter = listUserAdapter

                            listUserAdapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallback {
                                override fun onItemClicked(data: User) {
                                    showSelectedUser(data)
                                }

                            })
                            binding.rvFollow.layoutManager = LinearLayoutManager(context)
                        }
                        is com.takeshi.gouda.Result.Error -> {
                            showLoading(false)
                            Toast.makeText(context, "Error : ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showSelectedUser(data: User) {
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

    private fun showLoading(isLoading: Boolean) {
        binding.pgUser.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}