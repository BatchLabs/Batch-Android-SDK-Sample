package com.batchlabs.android.batchstore.UI.Data.Models

import java.io.Serializable

class Article : Serializable {
    var name:String
    var price:Int
    var image:Int

    constructor(name:String,price:Int,image:Int){
        this.name = name
        this.price = price
        this.image = image
    }
}