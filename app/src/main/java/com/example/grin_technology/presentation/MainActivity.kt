package com.example.grin_technology.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grin_technology.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModel: UserViewModel
    private val userAdapter = UserAdapter()
    private lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context=this

        setupRecyclerView()
        setupObservers()
        setupSwipeToRefresh()
    }
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    Log.d("Diraj","VisibleItemCount: $visibleItemCount")
                    val totalItemCount = layoutManager.itemCount
                    Log.d("Diraj","TotalItemCount: $totalItemCount")
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (!viewModel.isLoading.value!! && firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                        viewModel.loadNextPage()
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.users.observe(this) { users ->
            userAdapter.addUsers(users)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            userAdapter.addUsers(emptyList()) // Clear the list
            viewModel.loadNextPage() // Reload the first page
        }
    }
}
