package com.emedinaa.kotlinmvvm.model

import java.io.Serializable


data class User(val id:Int, val name:String, val email:String, val username:String,val address:Address, val  phone:String,val  website:String,val  company:Company):Serializable