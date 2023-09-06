package com.dht.gradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author dht
 * @date 2023/9/6 16:56
 **/
class NotesPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        println("----------------------p0.name = ${p0.name}----------------------")
    }
}