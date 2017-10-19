package com.kazakago.factolize

@Target(AnnotationTarget.FIELD)
annotation class FactoryParam(val required: Boolean = true)