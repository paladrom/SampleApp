package com.example.sampleapp

import com.example.domain.domain.SearchGitRepositoriesUseCase
import com.example.domain.domain.StarredRepositoriesUseCase
import com.example.domain.model.GitRepository
import com.example.domain.model.GitResult
import com.example.domain.model.GitSearchState
import com.example.sampleapp.feature.gitRepositorySearch.viewmodel.GitRepositorySearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GitRepositorySearchViewModelTest {

    @MockK(relaxed = true)
    private lateinit var starredRepositoriesUseCase: StarredRepositoriesUseCase

    @MockK(relaxed = true)
    private lateinit var searchGitRepositoriesUseCase: SearchGitRepositoriesUseCase
    private lateinit var viewModel: GitRepositorySearchViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel =
            GitRepositorySearchViewModel(
                searchGitRepositoriesUseCase = searchGitRepositoriesUseCase,
                starredRepositoriesUseCase = starredRepositoriesUseCase,
                starRepositoriesUseCase = mockk()
            )
    }

    @ExperimentalCoroutinesApi
    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testPerformSearchSuccessReturnsList() = runTest {
        val mockedResult = mockSearchResult()
        coEvery { searchGitRepositoriesUseCase.invoke(any()) } returns GitResult.Success(
            mockedResult
        )
        viewModel.performSearch("test")
        val state = viewModel.state.value
        require(state is GitSearchState.Success)
        Assert.assertTrue(state.repositories.size == 3)
        Assert.assertEquals(state.repositories.first().name, mockedResult.first().name)
        Assert.assertEquals(state.repositories[0].name, mockedResult[0].name)
        Assert.assertEquals(state.repositories[1].name, mockedResult[1].name)
        Assert.assertEquals(state.repositories[2].name, mockedResult[2].name)
    }

    @Test
    fun testInitState() {
        val state = viewModel.state.value
        Assert.assertTrue(state is GitSearchState.Init)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSuccessState() = runTest {
        coEvery { searchGitRepositoriesUseCase.invoke(any()) } returns GitResult.Success(
            mockSearchResult()
        )
        viewModel.performSearch("test")
        val state = viewModel.state.value
        Assert.assertTrue(state is GitSearchState.Success)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testErrorState() = runTest {
        coEvery { searchGitRepositoriesUseCase.invoke(any()) } throws IOException()
        viewModel.performSearch("test")
        val state = viewModel.state.value
        require(state is GitSearchState.Error)
        Assert.assertTrue(state.exception is IOException)
    }

    private fun mockSearchResult(): List<GitRepository> {
        val mockRepository1 =
            GitRepository(
                name = "nowInAndroid",
                ownerName = null,
                ownerIconUrl = null,
                url = "test",
                language = null,
                stargazersCount = null,
                watchersCount = null,
                forksCount = null,
                openIssuesCount = null,
                isStarred = false
            )
        return listOf(
            mockRepository1, mockRepository1.copy(name = "iosched"), mockRepository1.copy("tivi")
        )
    }
}