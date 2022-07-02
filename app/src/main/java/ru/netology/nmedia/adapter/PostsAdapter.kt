package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.R.menu
import ru.netology.nmedia.databinding.PostBinding

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {


<<<<<<< HEAD
=======

>>>>>>> 9ff8c82109fa14055b785435dc4e48df1ce61f65
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(
        private val binding: PostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
<<<<<<< HEAD
            binding.like.setOnClickListener { listener.onLikeClicked(post) }
            binding.share.setOnClickListener { listener.onShareClicked(post) }
            binding.options.setOnClickListener { popupMenu.show() }
        }

        fun bind(post: Post) {
=======
            binding.likes.setOnClickListener { listener.onLikeClicked(post) }
            binding.share.setOnClickListener { listener.onShareClicked(post) }
            //binding.options.setOnClickListener { popupMenu.show() }
        }

        fun bind(post: Post ) {
>>>>>>> 9ff8c82109fa14055b785435dc4e48df1ce61f65
            this.post = post
            with(binding) {
                authorName.text = post.author
                postText.text = post.content
                date.text = post.published
<<<<<<< HEAD
                like.text = formatEnds(post.likes)
                share.text = formatEnds(post.share)
                like.isChecked = post.likedByMe
=======
                likeCount.text = formatEnds(post.likes)
                shareCount.text = formatEnds(post.share)
                likes.setImageResource(getLikeIconResId(post.likedByMe))
                likes.setOnClickListener { interactionListener.onLikeClicked(post) }
                share.setOnClickListener { interactionListener.onShareClicked(post) }
                options.setOnClickListener { popupMenu.show() }

>>>>>>> 9ff8c82109fa14055b785435dc4e48df1ce61f65
            }
        }

        private fun formatEnds(shareCount: Int): String {
<<<<<<< HEAD
            if (shareCount >= 1000000) return String.format(
                "%.2f M",
                (shareCount / 1000000).toFloat()
            )
=======
            if (shareCount >= 1000000) return String.format("%.2f M", (shareCount / 1000000).toFloat())
>>>>>>> 9ff8c82109fa14055b785435dc4e48df1ce61f65
            if (shareCount >= 10000) return String.format("%d K", shareCount / 1000)
            if (shareCount >= 1000) return String.format("%.1f K", (shareCount / 1000).toFloat())
            return shareCount.toString()
        }

<<<<<<< HEAD

    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
=======
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
>>>>>>> 9ff8c82109fa14055b785435dc4e48df1ce61f65
}
