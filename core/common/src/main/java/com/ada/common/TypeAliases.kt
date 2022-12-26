package com.ada.common


/* Click types */
typealias OnClick = () -> Unit

typealias OnClickTakes<T> = (T) -> Unit

/* Navigation types */
typealias SimpleNavigation = () -> Unit

typealias SimpleNavigationTakes<T> = (T) -> Unit

/**
 * Used as ID for database entries.
 */
typealias Key = String

