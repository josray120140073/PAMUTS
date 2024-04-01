package com.example.pamuts120140073

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private var originalUserList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var filteredUserList: List<User> = originalUserList

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = filteredUserList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return filteredUserList.size
    }

    fun setData(newList: List<User>) {
        originalUserList = newList
        filter("")
    }

    fun filter(query: String) {
        filteredUserList = if (query.isBlank()) {
            originalUserList
        } else {
            originalUserList.filter { user ->
                user.first_name.contains(query, ignoreCase = true) ||
                        user.last_name.contains(query, ignoreCase = true) ||
                        user.email.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView =
            itemView.findViewById(R.id.avatarImageView)
        private val nameTextView: TextView =
            itemView.findViewById(R.id.nameTextView)
        private val emailTextView: TextView =
            itemView.findViewById(R.id.emailTextView)

        fun bind(user: User) {
            Glide.with(itemView.context).load(
                user.avatar).into(avatarImageView)
            nameTextView.text = "${user.first_name} ${user.last_name}"
            emailTextView.text = user.email
        }
    }
}