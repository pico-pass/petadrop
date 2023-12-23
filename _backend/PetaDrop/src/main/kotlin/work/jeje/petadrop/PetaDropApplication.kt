package work.jeje.petadrop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetaDropApplication

fun main(args: Array<String>) {
	runApplication<PetaDropApplication>(*args)
}
