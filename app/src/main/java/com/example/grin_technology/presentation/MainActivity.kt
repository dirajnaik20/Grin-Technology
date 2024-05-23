
package com.example.grin_technology.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
    private val userAdapter = UserAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupSwipeToRefresh()

        binding.progressBar.visibility = View.VISIBLE
        binding.swipeRefreshLayout.visibility = View.GONE
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
                    Log.d("Diraj","visibleItemCount: $visibleItemCount")
                    val totalItemCount = layoutManager.itemCount
                    Log.d("Diraj","$totalItemCount")
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

            if (users.isNotEmpty()) {
                // Hide the ProgressBar and show the RecyclerView once data is loaded
                binding.progressBar.visibility = View.GONE
                binding.swipeRefreshLayout.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.swipeRefreshLayout.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.swipeRefreshLayout.visibility = View.VISIBLE
            }
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            userAdapter.addUsers(emptyList()) // Clear the list
            viewModel.loadNextPage()
        }
    }

//    private fun progressBar(){
//        if (viewModel.isLoading.value==true){
//            binding.progressBar.visibility = View.VISIBLE
//        }
//        else if(viewModel.isLoading.value==false){
//            binding.progressBar.visibility = View.INVISIBLE
//        }
//    }

}
