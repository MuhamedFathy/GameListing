package com.github.data.extensions

/**
 * Authored by Mohamed Fathy on 26 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

fun <T : Comparable<T>> List<T>.sortDescending(): List<T> {
  val list = this
  if (list.count() <= 1) return list
  val middle = list.count().div(2)
  val left = list.subList(0, middle)
  val right = list.subList(middle, list.count())
  return merge(left.sortDescending(), right.sortDescending())
}

private fun <T : Comparable<T>> merge(left: List<T>, right: List<T>): List<T> {
  var indexLeft = 0
  var indexRight = 0
  val newList = mutableListOf<T>()

  while ((indexLeft < left.count()).and(indexRight < right.count())) {
    if (left[indexLeft] >= right[indexRight]) {
      newList.add(left[indexLeft])
      indexLeft++
    } else {
      newList.add(right[indexRight])
      indexRight++
    }
  }

  while (indexLeft < left.count()) {
    newList.add(left[indexLeft])
    indexLeft++
  }

  while (indexRight < right.count()) {
    newList.add(right[indexRight])
    indexRight++
  }
  return newList
}
