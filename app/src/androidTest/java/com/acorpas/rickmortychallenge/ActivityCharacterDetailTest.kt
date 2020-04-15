package com.acorpas.rickmortychallenge

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.runner.AndroidJUnit4
import com.acorpas.rickmortychallenge.domain.model.Character
import com.acorpas.rickmortychallenge.extension.fromJson
import com.acorpas.rickmortychallenge.ui.characterDetail.CharacterDetailActivity
import com.google.gson.Gson
import com.vsantander.speedrun.utils.activityTestRule
import okio.Okio
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.nio.charset.StandardCharsets

@RunWith(AndroidJUnit4::class)
class ActivityCharacterDetailTest {
    @Rule
    @JvmField
    val activityTestRule = activityTestRule<CharacterDetailActivity>(launchActivity = false)

    @Before
    fun setUp() {
        val fileContent = getFileContentAsString("character.json")
        val character = Gson().fromJson<Character>(fileContent)

        val i = Intent()
        i.putExtra(CharacterDetailActivity.EXTRA_CHARACTER, character)
        activityTestRule.launchActivity(i)
    }

    @Test
    fun infoViewIsShowing() {
        //Verify that the character info is setted in the textViews
        Espresso.onView(ViewMatchers.withId(R.id.titleTextView))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText("-"))))

        Espresso.onView(ViewMatchers.withId(R.id.text_status))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText("-"))))

        Espresso.onView(ViewMatchers.withId(R.id.text_gender))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText("-"))))

        Espresso.onView(ViewMatchers.withId(R.id.text_specie))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText("-"))))

        // Verify that the image is showing
        Espresso.onView(ViewMatchers.withId(R.id.characterImageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun backButtonClickTest() {
        Espresso.onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }


    @Throws(IOException::class)
    private fun getFileContentAsString(fileName: String): String {
        val inputStream =
            javaClass.classLoader.getResourceAsStream("sampledata/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        return source.readString(StandardCharsets.UTF_8)
    }
}