package divyansh.tech.animeclassroom.animeDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.EventObserver
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeClickCallback
import divyansh.tech.animeclassroom.animeDetail.epoxy.EpoxyAnimeDetailController
import divyansh.tech.animeclassroom.databinding.FragmentAnimeDetailsBinding

@AndroidEntryPoint
class AnimeDetailFragment: Fragment() {
    private lateinit var _binding: FragmentAnimeDetailsBinding
    val binding: FragmentAnimeDetailsBinding get() = _binding

    private val viewModel by viewModels<AnimeDetailViewModel>()
    private val args by navArgs<AnimeDetailFragmentArgs>()
    private val animeDetailController by lazy {
        val clickCallback = EpisodeClickCallback(viewModel)
        EpoxyAnimeDetailController(clickCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.animeDetailRv.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            animeDetailController.spanCount = 3
            (layoutManager as GridLayoutManager).spanSizeLookup = animeDetailController.spanSizeLookup
            adapter = animeDetailController.adapter
        }
    }

    private fun setupObservers() {
        viewModel.animeDetailLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResultWrapper.Success -> {
                        Log.i("ANIME DETAIL -> ", it.data.toString())
                        animeDetailController.setData(it.data, viewModel.episodeListLiveData.value?.data)
                    }
                    is ResultWrapper.Error -> Log.i("ANIME DETAIL", it.message.toString())
                    is ResultWrapper.Loading -> {}
                }
            }
        )

        viewModel.episodeListLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResultWrapper.Success -> animeDetailController.setData(viewModel.animeDetailLiveData.value?.data, it.data)
                    is ResultWrapper.Error -> Log.i("ANIME DETAIL", it.message.toString())
                    is ResultWrapper.Loading -> {}
                }
            }
        )

        viewModel.navigation.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigate(it)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAnimeDetails(args.animeUrl)
    }


}