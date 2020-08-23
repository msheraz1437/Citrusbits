package com.example.citrusbitsassignment.View.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emedinaa.kotlinmvvm.model.User
import com.example.citrusbitsassignment.R
import com.example.citrusbitsassignment.View.Activity.AlbumsActivity
import kotlinx.android.synthetic.main.user_detail_adapter_layout.view.*

class UserAdapter(private var userList:List<User>,val context: Context):RecyclerView.Adapter<UserAdapter.MViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_detail_adapter_layout, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        //render
        vh.bind(userList[position],context)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun update(data:List<User>){
        userList= data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val textViewName:TextView = view.name
        private val textViewUserName:TextView = view.userName
        private val textViewEmail:TextView = view.email
        private val textViewAddress:TextView = view.address
        private val textViewCompany:TextView = view.company
        private val textViewPhone:TextView = view.phone
        private val imageViewCollapse:ImageView = view.collapse
        private val leftLayout:RelativeLayout = view.leftLayout
        private val about:RelativeLayout = view.about
        private val rightLayout:RelativeLayout = view.rightLayout
        fun bind(user: User,context: Context){
            textViewName.text = user.name
            textViewUserName.text = user.username
            textViewEmail.text = user.email
            val address:String = user.address.street+" "+user.address.suite+" "+
                    user.address.city+" "+user.address.zipcode;
            textViewAddress.text = address
            val company:String = user.company.name+" "+user.company.catchPhrase+" "+
                    user.company.bs;

            textViewCompany.text = company
            textViewPhone.text = user.phone

            leftLayout.setOnClickListener(View.OnClickListener {  if(about.getVisibility() == View.GONE)
            {
                about.setVisibility(View.VISIBLE);
                imageViewCollapse.setImageResource(R.drawable.collapse_arrow);
            }
            else {
                about.setVisibility(View.GONE);
                imageViewCollapse.setImageResource(R.drawable.expends_arrow);

            } })
            rightLayout.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, AlbumsActivity::class.java)
                intent.putExtra("userId",user.id);
                intent.putExtra("userName",user.username);
                context.startActivity(intent);
            })

        }
    }
}