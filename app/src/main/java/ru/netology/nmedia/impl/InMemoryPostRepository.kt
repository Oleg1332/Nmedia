package ru.netology.nmedia.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                author = "Oleg",
                content = "Some random content $index",
                published = "10.06.2022",
                likes = 999,
                share = 999999
            )
        }
    )

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
    }