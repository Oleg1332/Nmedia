package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    val data: LiveData<List<Post>>

    fun like(postId: Long)

    fun share(postId: Long)
}