package com.acorpas.rickmortychallenge.ui.characterList

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import androidx.core.view.isVisible
import com.acorpas.rickmortychallenge.R
import com.acorpas.rickmortychallenge.di.Injectable
import com.acorpas.rickmortychallenge.domain.model.Status
import com.acorpas.rickmortychallenge.extension.logd
import com.acorpas.rickmortychallenge.extension.observe
import com.acorpas.rickmortychallenge.ui.characterDetail.CharacterDetailActivity
import com.acorpas.rickmortychallenge.ui.characterList.adapter.CharacterListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {

    companion object {
        private const val LIST_SPAN_COUNT = 2
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var adapter: CharacterListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.transition_fade_in, R.anim.transition_no_animation)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViews()
        setUpViewModels()
    }

    /* setUp methods */

    private fun setUpViews() {
        setUpToolbar()
        adapter = CharacterListAdapter {
            logd("ITEM PULSADO ${it.name}")

            startActivity(intentFor<CharacterDetailActivity>(
                CharacterDetailActivity.EXTRA_CHARACTER to it.id)
            )

        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(context,
                LIST_SPAN_COUNT)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setTitle(R.string.list_character_title)
    }

    private fun setUpViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CharacterListViewModel::class.java)


        viewModel.resource.observe(this) {
            it ?: return@observe

            progressBar.isVisible = it.status == Status.LOADING

            if (it.status == Status.SUCCESS) {
                adapter.setItems(it.data!!)
            } else if (it.status == Status.FAILED) {
                Snackbar.make(recyclerView, R.string.common_error, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        viewModel.loadCharacterList()
    }



}
