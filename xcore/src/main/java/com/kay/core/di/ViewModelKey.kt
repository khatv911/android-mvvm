package com.kay.core.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.ElementType
import java.lang.annotation.Target
import kotlin.reflect.KClass

/**
 * Created by KhaTran on 2/2/18.
 */
@kotlin.annotation.MustBeDocumented
@Target(ElementType.METHOD)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)