package io.github.lumkit.desktop.annotates

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
annotation class IntDef(vararg val value: Int)