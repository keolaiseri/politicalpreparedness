package com.example.android.politicalpreparedness.ui

import com.example.android.politicalpreparedness.data.Election

class ElectionItemClickListener(private val clickListener: (Election) -> Unit) {
    fun onClick(data: Election) = clickListener(data)
}