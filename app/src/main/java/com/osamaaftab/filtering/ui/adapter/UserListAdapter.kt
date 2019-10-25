package com.osamaaftab.filtering.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osamaaftab.filtering.R
import com.osamaaftab.filtering.ui.model.UserData
import kotlinx.android.synthetic.main.layout_user_list.view.*
import javax.inject.Inject

class UserListAdapter @Inject constructor(var context: Context) :


    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var userList: ArrayList<UserData> = ArrayList()

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        viewHolder.userName.text = userList[pos].display_name
        viewHolder.city.text = userList[pos].city!!.name.toString()
        viewHolder.age.text = userList[pos].age.toString()
        viewHolder.job.text = userList[pos].job_title.toString()
        viewHolder.religion.text = userList[pos].religion
        viewHolder.fav.text = userList[pos].favourite.toString()
        viewHolder.height.text = userList[pos].height_in_cm.toString()
        viewHolder.score.text = (userList[pos].compatibility_score?.times(100)).toString()
        viewHolder.user_contact.text = userList[pos].contacts_exchanged.toString()
        viewHolder.lat.text = userList[pos].city!!.lat.toString()
        viewHolder.lon.text = userList[pos].city!!.log.toString()
        Glide.with(context).load(userList[pos].main_photo).into(viewHolder.userPicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_user_list, parent, false)
        )
    }

    fun addList(remotelist: ArrayList<UserData>) {
        this.userList = remotelist
    }

    fun updateList(updatedList: ArrayList<UserData>) {
        this.userList.clear()
        this.userList = updatedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName = view.display_name
        val userPicture = view.user_picture
        val city = view.city
        val age = view.user_age
        val job = view.user_title_job
        val religion = view.user_religion
        val fav = view.user_fav
        val height = view.user_height
        val user_contact = view.user_contact
        val score = view.user_score
        val lat = view.user_lat
        val lon = view.user_lon
    }
}