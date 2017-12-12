package com.kazakago.factolize

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class FactoryParam(val required: Boolean = true)