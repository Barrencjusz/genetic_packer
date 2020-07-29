package genetic.selectors

import org.springframework.stereotype.Component
import java.util.concurrent.ThreadLocalRandom
import java.util.function.Supplier

class ThreadLocalRandomSupplier : () -> ThreadLocalRandom {

  override fun invoke() = ThreadLocalRandom.current()!!
}