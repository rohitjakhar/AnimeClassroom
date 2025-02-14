package divyansh.tech.animeclassroom.favorites.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.databinding.FragmentDynamicTabBinding


@AndroidEntryPoint
class DynamicTabFragment : Fragment() {

    companion object {

        private const val CATEGORY = "category"
        fun getInstance(category: String) = DynamicTabFragment().apply {
            arguments = bundleOf(CATEGORY to category)
        }

    }

    private lateinit var _dynamicTabFragment: FragmentDynamicTabBinding
    val binding: FragmentDynamicTabBinding get() = _dynamicTabFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _dynamicTabFragment= FragmentDynamicTabBinding.inflate(inflater, container, false)
        return binding.root
    }

}