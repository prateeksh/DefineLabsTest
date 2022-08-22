package com.company.definelabstest.ui.savedmatches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.definelabstest.adapter.MatchesAdapter
import com.company.definelabstest.adapter.SavedMatchesAdapter
import com.company.definelabstest.databinding.FragmentSavedMatchesBinding
import com.company.definelabstest.model.Matches
import com.company.definelabstest.room.MatchesDatabase
import com.company.definelabstest.ui.MainApplication
import com.company.definelabstest.ui.allmatches.viewmodel.HomeViewModel
import com.company.definelabstest.ui.allmatches.viewmodel.HomeViewModelFactory
import com.company.definelabstest.ui.savedmatches.viewmodel.SavedMatchesViewModel
import com.company.definelabstest.ui.savedmatches.viewmodel.SavedViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SavedMatchesFragment : Fragment() {

    private var _binding: FragmentSavedMatchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var savedMatchesViewModel: SavedMatchesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SavedMatchesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavedMatchesBinding.inflate(inflater, container, false)

        val repository = (requireActivity().application as MainApplication).repository

        savedMatchesViewModel =
            ViewModelProvider(requireActivity(), SavedViewModelFactory(repository))
                .get(SavedMatchesViewModel::class.java)

        recyclerView = binding.savedMatchesView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedMatchesViewModel.init()
        savedMatchesViewModel.saved.observe(viewLifecycleOwner, Observer {
            adapter = SavedMatchesAdapter(savedMatchesViewModel.saved.value!!, requireContext())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

            adapter.onItemClick = { venues ->
                CoroutineScope(Dispatchers.IO).launch {
                    val database = MatchesDatabase.getDatabase(requireContext())
                    venues.id?.let { it1 -> database.userDao().deleteMatches(it1) }
                }
                val snack = Snackbar.make(view,venues.name + " deleted from db",Snackbar.LENGTH_LONG)
                snack.show()
            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}