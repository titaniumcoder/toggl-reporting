package io.kotlintest.provided

import io.kotlintest.AbstractProjectConfig
import io.micronaut.test.extensions.kotlintest.MicronautKotlinTestExtension

object ProjectConfig : AbstractProjectConfig() {
    private var started: Long = 0

    override fun parallelism(): Int = 1 // TODO check what is useful here

    override fun listeners() = listOf(MicronautKotlinTestExtension)
    override fun extensions() = listOf(MicronautKotlinTestExtension)

    override fun beforeAll() {
        started = System.currentTimeMillis()
    }

    override fun afterAll() {
        val time = System.currentTimeMillis() - started

        println("overall time [ms]: $time")
    }
}