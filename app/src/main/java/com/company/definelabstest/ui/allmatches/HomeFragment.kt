package com.company.definelabstest.ui.allmatches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.definelabstest.adapter.MatchesAdapter
import com.company.definelabstest.databinding.FragmentAllMatchesBinding
import com.company.definelabstest.room.MatchesDatabase
import com.company.definelabstest.ui.MainApplication
import com.company.definelabstest.ui.allmatches.viewmodel.HomeViewModel
import com.company.definelabstest.ui.allmatches.viewmodel.HomeViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentAllMatchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: MatchesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAllMatchesBinding.inflate(inflater, container, false)

        recyclerView = binding.allMatchesView

        val repository = (requireActivity().application as MainApplication).repository

         homeViewModel =
            ViewModelProvider(requireActivity(), HomeViewModelFactory(repository))
                .get(HomeViewModel::class.java)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.user.observe(viewLifecycleOwner, Observer {

            adapter = MatchesAdapter(homeViewModel.user.value!!, requireContext())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

            adapter.onItemClick = { venues ->
                CoroutineScope(Dispatchers.IO).launch{
                    val database = MatchesDatabase.getDatabase(requireContext())
                    venues.verified = true
                    val count: Int = database.userDao().count(venues.id)

                    if ( count == 1) {

                        //to delete from database
                        venues.id?.let { it1 -> database.userDao().deleteMatches(it1) }
                        val snack = Snackbar.make(view,venues.name + " deleted from db", Snackbar.LENGTH_SHORT)
                        snack.show()

                    } else {

                        database.userDao().insertVenues(venues)
                        val snack = Snackbar.make(view,venues.name + " inserted in db", Snackbar.LENGTH_SHORT)
                        snack.show()

                    }
                }

            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}