package com.github.domain.exception

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class NetworkNotAvailableException : Throwable("Network is not available.")
class DataRetrievingFailException : Throwable("Error getting data from server.")
class NoDataException : Throwable("No data.")