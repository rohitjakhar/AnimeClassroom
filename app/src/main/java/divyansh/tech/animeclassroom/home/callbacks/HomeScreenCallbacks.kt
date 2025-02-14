package divyansh.tech.animeclassroom.home.callbacks

import divyansh.tech.animeclassroom.animeDetail.AnimeDetailFragmentDirections
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.Clicks
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.home.HomeFragmentDirections
import divyansh.tech.animeclassroom.models.home.GenreModel
import divyansh.tech.animeclassroom.searchAnime.SearchAnimeFragmentDirections

/*
* Collection of click callbacks on the home screen
* */
class HomeScreenCallbacks(private val viewModel: CommonViewModel): AnimeClickCallback {

    /*
    * When user clicks on popular animes, movies or new seasons
    * */

    override fun onAnimeClicked(animeUrl: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToAnimeDetailFragment(animeUrl)
        viewModel.changeNavigation(action)
    }

    override fun onEpisodeClicked(episodeUrl: String) {
        val action = HomeFragmentDirections.actionGlobalPlayerActivity(episodeUrl)
        viewModel.changeNavigation(action)
    }

    override fun onGenreClicked(genreUrl: String) {}
}