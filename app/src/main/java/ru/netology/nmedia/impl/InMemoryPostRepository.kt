package ru.netology.nmedia.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Oleg",
                content = "Some random content $index",
                published = "10.06.2022",
                likes = 999,
                share = 999999,
                video = "https://youtu.be/dQw4w9WgXcQ"
            )
        }
    )

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(likedByMe = !it.likedByMe)
                .also { post -> if (post.likedByMe) post.likes++ else post.likes-- }
        }

    }



    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(share = it.share)
                .also { post -> post.share++ }
        }

}

    override fun delete(postId: Long) {
        data.value = posts.filterNot { it.id == postId }

    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
    }
}