package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.SeparatedPostViewBinding
import ru.netology.nmedia.viewModel.PostViewModel


class SeparatedPostFragment: Fragment() {
    private val args by navArgs<SeparatedPostFragmentArgs>()

    private val separatedPostViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SeparatedPostViewBinding.inflate(layoutInflater, container, false).also { binding ->
        val postViewHolder =
            PostsAdapter.ViewHolder(binding.separatePostView, separatedPostViewModel)
        separatedPostViewModel.data.observe(viewLifecycleOwner) { posts ->
            val separatedPost = posts.find { it.id == args.postCardId } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            postViewHolder.bind(separatedPost)
        }

        separatedPostViewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { textToEdit ->
            val direction = SeparatedPostFragmentDirections.toPostContentFragment(textToEdit)
            findNavController().navigate(direction)
        }
        separatedPostViewModel.videoPlayEvent.observe(viewLifecycleOwner) { videoLink ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
            startActivity(intent)
        }
        separatedPostViewModel.sharePostContent.observe(viewLifecycleOwner) {
                postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(
                    intent,
                    getString(R.string.description_post_share)
                )
            startActivity(shareIntent)
        }

        setFragmentResultListener(
            requestKey = PostContentFragment.REQUEST_KEY_2
        ) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY_2) return@setFragmentResultListener
            val newPostContent = bundle.getString(
                PostContentFragment.RESULT_KEY_2
            ) ?: return@setFragmentResultListener
            separatedPostViewModel.onSaveButtonClicked(newPostContent)
        }
    }.root
}