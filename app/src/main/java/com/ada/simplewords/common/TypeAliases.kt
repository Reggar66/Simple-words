package com.ada.simplewords.common

typealias OnClick = () -> Unit

typealias OnClickTakes<T> = (T) -> Unit

/**
 * Used as ID for database entries.
 */
typealias Key = String