package com.example.android.politicalpreparedness.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.android.politicalpreparedness.data.Election

class ElectionDiffCallback: DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }
}