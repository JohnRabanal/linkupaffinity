package com.example.linkupaffinity.screens.home

interface DashboardContract {
    interface View {
        fun showLoading()
        fun showDashboardData(
            userName: String,
            upcomingEventTitle: String,
            upcomingEventDate: String,
            dailyProgress: Int,
            searchBadges: Int,
            matchBadges: Int

            )
        fun showEmptyState()
        fun navigateToLogin()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadDashboardContent()
        fun onRefreshTriggered()
        fun onLogoutClicked()
    }
}