package ru.netology.nmedia.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import kotlin.properties.Delegates

internal class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(private val binding: PostBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likes.setOnClickListener { onLikeClicked(post) }
            binding.share.setOnClickListener { onShareClicked(post) }
        }
        fun bind(post: Post) = with(binding) {
            authorName.text = post.author
            postText.text = post.content
            date.text = post.published
            likeCount.text = formatEnds(post.likes)
            shareCount.text = formatEnds(post.share)
            likes.setImageResource(getLikeIconResId(post.likedByMe))
            likes.setOnClickListener { onLikeClicked(post) }
            share.setOnClickListener { onShareClicked(post) }
        }

        private fun formatEnds(shareCount: Int): String {
            if (shareCount >= 1000000) return String.format("%.2f M", (shareCount / 1000000).toFloat())
            if (shareCount >= 10000) return String.format("%d K", shareCount / 1000)
            if (shareCount >= 1000) return String.format("%.1f K", (shareCount / 1000).toFloat())
            return shareCount.toString()
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_like_red_24dp else R.drawable.ic_like_24dp
    }

}
private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) =
        oldItem == newItem
}
