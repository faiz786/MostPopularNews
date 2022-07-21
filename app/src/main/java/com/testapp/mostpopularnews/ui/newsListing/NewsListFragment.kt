package com.testapp.mostpopularnews.ui.newsListing

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.testapp.mostpopularnews.R
import com.testapp.mostpopularnews.data.entities.NewsDbEntity
import com.testapp.mostpopularnews.data.models.News
import com.testapp.mostpopularnews.data.models.NewsImage
import com.testapp.mostpopularnews.databinding.NewsListFragmentBinding
import com.testapp.mostpopularnews.utils.Resource
import com.testapp.mostpopularnews.utils.isConnectedToInternet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    lateinit var binding: NewsListFragmentBinding
    private val viewModel: NewsListingViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    // get the arguments from the Registration fragment
    private var lastBackClick: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsListFragmentBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.layoutToolbar.materialToolbar)
        setupDrawerLayout()

        binding.swipeRefreshLayout.setOnRefreshListener(this::fetchDataFromRemote)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        fetchDataFromRemote()
    }

    private fun setupRecyclerView() {
        val listener = NewsAdapter.OnClickListener() { newsSelected ->

//            val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(newsSelected)
//
//            findNavController().navigate(action)

            val bundle = Bundle()
            bundle.putSerializable("news_obj", newsSelected)
            findNavController().navigate(
                R.id.action_newsListFragment_to_newsDetailFragment,
                bundle
            )


        }
        adapter = NewsAdapter(listener)
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getNewsFromDatabase().observe(requireActivity(), Observer {
            var newsList: ArrayList<News> = ArrayList()
            var newsImages: ArrayList<NewsImage> = ArrayList()

            for (i in it) {
//                newsImages.add(NewsImage(i.caption, i.copyright, i.imageUrl))
                var news = News(
                    i.id,
                    i.title,
                    i.newsAbstract,
                    i.publishDate,
                    i.category,
                    i.author,
                    i.source,
                    i.url,
                    i.imageUrl
                )
                newsList.add(news)
            }
            adapter.submitList(newsList)
        })

    }

    private fun fetchDataFromRemote() {
        binding.swipeRefreshLayout.isRefreshing = true
        binding.swipeRefreshLayout.isEnabled = false
        if (requireActivity().isConnectedToInternet()) {
            viewModel.getNewsFromRemote().observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.linearProgressIndicator.visibility = View.GONE
                        if (it.data != null) {
                            var newsList: ArrayList<News> = ArrayList()
                            var newsDBList: ArrayList<NewsDbEntity> = ArrayList()
                            lateinit var news:News
                            lateinit var newsDbEntity: NewsDbEntity
                            for (i in it.data.body()!!.newsItemRetros) {

                                for (media in i.mediaItemRetros) {
//                                    if (media.mediaMetadataRetros.isNotEmpty()) {
//                                        val largestImageUrl =
//                                            media.mediaMetadataRetros[media.mediaMetadataRetros.size - 1].url
//                                        images.add(
//                                            NewsImage(
//                                                media.caption,
//                                                media.copyright,
//                                                largestImageUrl
//                                            )
//                                        )
//                                    }

//                                for (j in i.mediaItemRetros) {
//                                    var newsImage = NewsImage(
//                                        j.caption,
//                                        j.copyright,
//                                        j.mediaMetadataRetros[0].url
//                                    )
//                                    newsImages.add(newsImage)
                                    newsDbEntity = NewsDbEntity(
                                        i.id,
                                        i.title,
                                        i.newsAbstract,
                                        i.publishedDate,
                                        i.type,
                                        i.author,
                                        i.source,
                                        i.url,
                                        media.caption,
                                        media.copyright,
                                        media.mediaMetadataRetros[media.mediaMetadataRetros.size - 1].url
                                    )

                                    news = News(
                                        i.id,
                                        i.title,
                                        i.newsAbstract,
                                        i.publishedDate,
                                        (i.section + " " + i.subsection).trim(),
                                        i.author,
                                        i.source,
                                        i.url,
                                        media.mediaMetadataRetros[media.mediaMetadataRetros.size - 1].url
                                    )
//                                }
                                }

                                newsList.add(news)
                                newsDBList.add(newsDbEntity)
                            }

//                            adapter.submitList(newsList)
                            viewModel.viewModelScope.launch {
                                if (binding.swipeRefreshLayout.isRefreshing) {
                                    adapter.submitList(newsList)
                                    viewModel.insertAllNewsInDB(newsDBList)
                                }
                                else
                                    viewModel.insertAllNewsInDB(newsDBList)
                            }

                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.swipeRefreshLayout.isEnabled = true


                        }
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(
                            requireContext(),
                            "Error in fetching news " + it.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    Resource.Status.LOADING ->
                        binding.linearProgressIndicator.visibility = View.VISIBLE
                }
            })
        }


//        else {
//            viewModel.getNewsFromDatabase().observe(requireActivity(), Observer {
//                var newsList: ArrayList<News> = ArrayList()
//                var newsImages: ArrayList<NewsImage> = ArrayList()
//
//                for (i in it) {
//                    newsImages.add(NewsImage(i.caption, i.copyright, i.imageUrl))
//                    var news = News(
//                        i.id,
//                        i.title,
//                        i.newsAbstract,
//                        i.publishDate,
//                        i.category,
//                        i.author,
//                        i.source,
//                        i.url,
//                        newsImages
//                    )
//                    newsList.add(news)
//                }
//                adapter.submitList(newsList)
//            })
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_options_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_news)
        searchView.setOnCloseListener {
//            viewModel.performEvent(ItemListViewModel.ListUiEvent.Search(null))
//            viewModel.performEvent(ItemListViewModel.ListUiEvent.Search(null))
            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    viewModel.getNewsFromRemoteQuerySearch(newText!!)
                        .observe(requireActivity(), Observer {
                            if(it.isNotEmpty()) {
                                var newsList: ArrayList<News> = ArrayList()

                                for (i in it) {
                                    var news = News(
                                        i.id,
                                        i.title,
                                        i.newsAbstract,
                                        i.publishDate,
                                        i.category,
                                        i.author,
                                        i.source,
                                        i.url,
                                        i.imageUrl
                                    )
                                    newsList.add(news)
                                }
                                adapter.submitList(newsList)
                            }
                        })
                }
                return true
            }
        })

//        val query = itemListViewModel.listUiState.value?.query
        if (searchView.query != null && searchView.query.isNotEmpty()) {
            searchView.isIconified = false
            searchView.setIconifiedByDefault(false)
            searchItem.expandActionView()
            searchView.requestFocus()
            searchView.setQuery(searchView.query, false)
        }
    }

    private fun setupDrawerLayout() {
        val itemListContainer = binding.itemListContainer
        val navigationView = binding.layoutNavigationDrawer.navigationView
        binding.layoutToolbar.materialToolbar.setNavigationOnClickListener {
            itemListContainer.openDrawer(
                navigationView
            )
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when {
                        itemListContainer.isDrawerOpen(navigationView) -> {
                            itemListContainer.closeDrawer(navigationView)
                        }
                        // Pressed back twice
                        (lastBackClick + BACK_DELAY) > System.currentTimeMillis() -> {
                            activity?.finish()
                        }
                        else -> {
                            Toast.makeText(
                                context,
                                getString(R.string.press_back_again),
                                Toast.LENGTH_SHORT
                            ).show()
                            lastBackClick = System.currentTimeMillis()
                        }
                    }
                }
            })
        navigationView.setNavigationItemSelectedListener {
            itemListContainer.closeDrawer(navigationView)

            when (it.itemId) {
                R.id.nav_drawer_share -> {
                    val shareIntent = ShareCompat
                        .IntentBuilder(requireContext())
                        .setText(getString(R.string.share_message))
                        .setType("text/*")
                        .createChooserIntent()

                    requireContext().packageManager.resolveActivity(
                        shareIntent,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )
                        ?.apply {
                            requireContext().startActivity(shareIntent)
                        }
                    true
                }
//                R.id.nav_drawer_create_news -> {
//                    val news = createRandomNews()
//                    itemListViewModel
//                        .performEvent(
//                            ItemListViewModel.ListUiEvent.CreateNews(
//                                news.title,
//                                news.newsAbstract,
//                                news.publishDate,
//                                news.category,
//                                news.author,
//                                news.source,
//                                news.url,
//                                news.images
//                            )
//                        )
//                    true
//                }
                else -> {
                    true
                }
            }
        }
    }


//    override fun onClickedCharacter(characterId: Int) {
//        findNavController().navigate(
//            R.id.action_charactersFragment_to_characterDetailFragment,
//            bundleOf("id" to characterId)
//        )
//    }

    companion object {
        const val BACK_DELAY = 2000L
    }
}
