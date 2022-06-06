package ru.netology.nmedia.impl
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository: PostRepository {
    override val data = MutableLiveData(
        Post(
        id = 0L,
        author = "Oleg",
        content = "Events",
        published = "11.05.2022",
        likes = 999,
        share = 999999
    )
    )

    override fun like() {

        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe
        )
        data.value = if(likedPost.likedByMe){
            likedPost.copy(likes = likedPost.likes + 1)
        } else {
            likedPost.copy(likes = likedPost.likes - 1)
        }

    }


    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        var sharedCount = currentPost.share
        val sharedPost = currentPost.copy(
            share = sharedCount + 10

        )

        data.value = sharedPost
    }



    override fun formatEnds(shareCount: Int): String {
        if (shareCount >= 1000000) return String.format("%.2f M", (shareCount / 1000000).toFloat())
        if (shareCount >= 10000) return String.format("%d K", shareCount / 1000)
        if (shareCount >= 1000) return String.format("%.1f K", (shareCount / 1000).toFloat())
        return shareCount.toString()
    }
}