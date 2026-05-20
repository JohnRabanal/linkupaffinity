package com.example.linkupaffinity.screens.home

import kotlinx.coroutines.*

class DashboardPresenter : DashboardContract.Presenter {
    private var view: DashboardContract.View? = null
    // Changed to SupervisorJob() to keep the scope healthy if individual tasks cancel
    private val presenterScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun attachView(view: DashboardContract.View) {
        this.view = view
        loadDashboardContent()
    }

    override fun detachView() {
        this.view = null
        presenterScope.cancel()
    }

    override fun loadDashboardContent() {
        view?.showLoading()

        // Simulating network delay to showcase your custom loading state spinner
        presenterScope.launch {
            delay(1500)

            // 🔥 FIXED: Passing explicit counts to separate the numbers from row titles
            view?.showDashboardData(
                userName = "User",
                upcomingEventTitle = "Team Building Picnic",
                upcomingEventDate = "June 15, 2026",
                dailyProgress = 8,
                searchBadges = 6,   // Change to 0 when there are no unread alerts
                matchBadges = 10    // Change to 0 when there are no unread alerts
            )
        }
    }

    override fun onRefreshTriggered() {
        loadDashboardContent()
    }

    override fun onLogoutClicked() {
        view?.navigateToLogin()
    }
}