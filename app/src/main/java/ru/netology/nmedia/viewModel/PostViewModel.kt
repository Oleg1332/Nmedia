package ru.netology.nmedia.viewModel

import SingleLiveEvent
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.impl.FilePostRepository
import ru.netology.nmedia.impl.InMemoryPostRepository
import ru.netology.nmedia.impl.SharedPrefsPostRepository


class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = SharedPrefsPostRepository(application)

    val data by repository::data

    val videoPlayEvent = SingleLiveEvent<String>()
    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val separatePostViewEvent = SingleLiveEvent<Long>()
    private val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return
        val newPost = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "now"
        )
        repository.save(newPost)
        currentPost.value = null
    }

// region PostInteractionListener

    fun onCancelButtonClicked() {
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) =
        repository.like(post.id)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content
    }
    override fun onRemoveClicked(post: Post) =
        repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }
    override fun onVideoPlayClicked(post: Post) {
        currentPost.value = post
        videoPlayEvent.value = post.video
    }
    override fun onPostCardClicked(post: Post) {
        currentPost.value = post
        separatePostViewEvent.value = post.id
    }
    // endregion PostInteractionListener
}