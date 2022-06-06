package ru.netology.nmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

const val THOUSAND = 1000.0
const val MILLION = 1000000.0
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      viewModel.data.observe(this) { post ->
          binding.render(post)
      }


        //var likeCount = binding.likeCount
        binding.likes.setOnClickListener {
            viewModel.onLikeClicked()
//            likeCount.text = formatEnds(post.likes)
//            binding.likes.setImageResource(getLikeIconResId(post.likedByMe))

        }
        //var viewShareCount = binding.shareCount
        binding.share.setOnClickListener {
            viewModel.onShareClicked()
//            post.share++
//            viewShareCount.text = formatEnds(post.share)
//            binding.share.setImageResource(R.drawable.ic_share_24dp)
        }
    }

    private fun formatEnds(shareCount: Int): String {
        if (shareCount >= 1000000) return String.format("%.2f M", (shareCount / 1000000).toFloat())
        if (shareCount >= 10000) return String.format("%d K", shareCount / 1000)
        if (shareCount >= 1000) return String.format("%.1f K", (shareCount / 1000).toFloat())
        return shareCount.toString()
    }


    private fun ActivityMainBinding.render(post: Post) {
        authorName.text = post.author
        postText.text = post.content
        date.text = post.published
        likeCount.text = post.likes.toString()
        shareCount.text = post.share.toString()
        likes.setImageResource(getLikeIconResId(post.likedByMe))
    }


    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_like_red_24dp else R.drawable.ic_like_24dp
}








