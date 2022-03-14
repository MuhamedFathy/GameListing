package com.github.gamelisting.resource

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class Resource<out T> constructor(
  val state: ResourceState,
  val data: T? = null,
)