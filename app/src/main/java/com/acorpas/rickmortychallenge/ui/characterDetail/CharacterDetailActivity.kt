package com.acorpas.rickmortychallenge.ui.characterDetail

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import com.acorpas.rickmortychallenge.di.Injectable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import androidx.core.view.isVisible
import com.acorpas.rickmortychallenge.R
import com.acorpas.rickmortychallenge.databinding.ActivityCharacterDetailBinding
import com.acorpas.rickmortychallenge.domain.model.Status
import com.acorpas.rickmortychallenge.extension.observe
import kotlinx.android.synthetic.main.activity_character_detail.*
import javax.inject.Inject


class CharacterDetailActivity : AppCompatActivity(), Injectable {

    companion object {
        const val EXTRA_CHARACTER = "EXTRA_CHARACTER"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CharacterDetailVewModel

    lateinit var mainBinding : ActivityCharacterDetailBinding

    var idCharacter = 0



    /* Activity methods */
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.transition_enter_right, R.anim.transition_fade_out)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail)
        mainBinding.setLifecycleOwner(this)
        intent?.extras?.getInt(EXTRA_CHARACTER)?.let {
            idCharacter = it
            setUpViews()
            setUpViewModel()
        } ?: throw RuntimeException("bad initialization. not found some extras")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle item selection
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setUpViews() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_white)
        supportActionBar?.setTitle(R.string.list_characterDetail_title)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CharacterDetailVewModel::class.java)

        viewModel.resource.observe(this) {
            it ?: return@observe

            progressBar.isVisible = it.status == Status.LOADING

            if (it.status == Status.SUCCESS) {
                mainBinding.characterVM = viewModel
                mainBinding.executePendingBindings()
            } else if (it.status == Status.FAILED) {
                Snackbar.make(characterImageView, R.string.common_error, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        viewModel.loadCharacterDetail(idCharacter)
    }
}