package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.databinding.PostContentActivityBinding
import ru.netology.nmedia.R

class PostContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val textToEdit = intent?.extras?.getString(Intent.EXTRA_TEXT) ?: ""
        binding.edit.setText(textToEdit)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.edit.text.isNullOrBlank()) {

                setResult(Activity.RESULT_CANCELED, intent)
            } else {

                val content = binding.edit.text.toString()
                intent.putExtra(RESULT_KEY, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }


    object ResultContract : ActivityResultContract<String?, String?>() {


        override fun createIntent(context: Context, input: String?) =
            Intent(context, PostContentActivity::class.java)
                .putExtra(Intent.EXTRA_TEXT, input)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            if (resultCode == Activity.RESULT_OK) {

                intent?.getStringExtra(RESULT_KEY)
            } else null
    }
    companion object {
        const val RESULT_KEY = "postNewContent"
    }
}
