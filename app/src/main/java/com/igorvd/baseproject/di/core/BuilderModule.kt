package com.igorvd.baseproject.di.core

import android.app.Activity
import android.app.Service
import android.support.v4.app.Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module that contains the [ContributesAndroidInjector] implementations for injecting the concrete
 * Android framework classes: [Activity] - [Fragment] - [Service]
 * @author Igor Vilela
 * @since 27/12/17
 */

@Module
abstract class BuilderModule {


    //**************************************************************************
    // region: Modules list
    //**************************************************************************

   /* @ContributesAndroidInjector(modules = arrayOf(
            ListModulesModule::class
    ))
    abstract fun contributesModule(): ModuleActivity*/

    //endregion
}