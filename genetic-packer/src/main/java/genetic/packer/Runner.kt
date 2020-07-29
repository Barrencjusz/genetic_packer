package genetic.packer

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Runner(private val fitnessLoggingAdapter: Runnable) : CommandLineRunner {

  override fun run(vararg strings: String) {
    fitnessLoggingAdapter.run()
  }
}