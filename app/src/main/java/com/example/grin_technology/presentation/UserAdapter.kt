package com.example.grin_technology.presentation

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.grin_technology.data.remote.model.User
import com.example.grin_technology.databinding.ItemUserBinding


class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//        val itemView: ItemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        itemView.root.layoutParams.width =
//            ( / 5) as Int /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS
//        return UserViewHolder(itemView)
//    }

//    fun Context.getScreenWidth(): Int {
//        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val display = wm.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//        return size.x
//    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        Log.d("Diraj","getItemCount: ${users.size}")
        return users.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(newUsers: List<User>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.nameTextView.text = "${user.firstName} ${user.lastName}"
            binding.emailTextView.text = user.email
            Glide.with(binding.avatarImageView.context)
                .load(user.avatar)
                .into(binding.avatarImageView)
        }
    }
}
