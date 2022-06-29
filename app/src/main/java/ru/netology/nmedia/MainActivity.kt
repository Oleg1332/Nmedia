package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.save.setOnClickListener{
            with(binding.content) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }

        binding.cancelButton.setOnClickListener {
            viewModel.onCancelButtonClicked()
        }

     viewModel.currentPost.observe(this){ currentPost ->
         with(binding.content) {
             val content = currentPost?.content
             setText(content)
             if (content != null) {
                 requestFocus()
                 showKeyboard()
                 binding.group.visibility = View.VISIBLE
             } else {
                 clearFocus()
                 hideKeyboard()
                 binding.group.visibility = View.GONE
             }
         }
     }
    }
}








