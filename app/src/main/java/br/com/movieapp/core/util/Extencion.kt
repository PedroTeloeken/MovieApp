package br.com.movieapp.core.util

import br.com.movieapp.BuildConfig

fun String.toPostUrl() = "${BuildConfig.BASE_URL_IMAGE}$this"

fun String.toBackDropUrl() = "${BuildConfig.BASE_URL_IMAGE}$this"