package com.example.recycler.personagefull

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.example.recycler.models.personage.Location
import com.example.recycler.models.personage.Origin
import com.example.recycler.models.personage.Personage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import coil.size.Scale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PersonageFullFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var personage: Personage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        personage = moshi.adapter(Personage::class.java).fromJson(param1!!)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = ComposeView(requireContext())
        view.setContent {

            PersonageItem(personage)
        }

        return view

    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            PersonageFullFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

@Composable
fun PersonageItem(personage: Personage) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberImagePainter(personage.image),
            contentDescription = personage.name,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp)
        ) {
            Text(text = personage.name, fontWeight = FontWeight.Bold, fontSize = 30.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 16.dp)
        ) {
            Text(text = "Live status:")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 16.dp)
        ) {
            Text(text = "Status: ${personage.status}", fontSize = 16.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 16.dp)
        ) {
            Text(text = "Species and ender:")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 16.dp)
        ) {
            Text(text = "${personage.species}(${personage.gender})", fontSize = 16.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 16.dp)
        ) {
            Text(text = "Last know location:")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 16.dp)
        ) {
            Text(text = "${personage.location.name}", fontSize = 16.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 16.dp)
        ) {
            Text(text = "First seen in:")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 16.dp)
        ) {
            Text(text = "${personage.type}", fontSize = 16.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 16.dp)
        ) {
            Text(text = "Episodes:")
        }

        personage.episode.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) { Text(text = it) }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun previewMyText() {
    PersonageItem(
        Personage(
            created = "2017-11-04T19:59:20.523Z",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            gender = "Male",
            id = 1,
            location = Location(
                name = "String",
                url = "String"
            ),
            origin = Origin(
                name = "String",
                url = "String"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            name = "Rick Sanchez",
            species = "Human",
            status = "Alive",
            type = "",
            url = "https://rickandmortyapi.com/api/character/1"
        )
    )
}