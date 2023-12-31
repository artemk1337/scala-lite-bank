package misis.kafka

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import io.circe.generic.auto._
import misis.WithKafka
import misis.model.{AccountUpdate, ExternalTransactionComplete, FeeRequest}
import misis.repository.CashbackRepository

import scala.concurrent.{ExecutionContext, Future}

class CashbackStreams(repository: CashbackRepository)(implicit
    val system: ActorSystem,
    executionContext: ExecutionContext
) extends WithKafka {
    override def group: String = s"cashback-${repository.CashBackCategory}"

    kafkaSource[ExternalTransactionComplete]
        .filter(command => repository.CashBackCategory == command.categoryId)
        .mapAsync(1) { command =>
            val cashback = repository.getCashback(command)
            if (cashback.is_allowed) {
                println(s"Кэшбек для категории ${command.categoryId} акканта ${command.srcAccountId} начислен")
                produceCommand(AccountUpdate(command.srcAccountId, cashback.value))
            } else {
                println(s"Кэшбек для категории ${command.categoryId} акканта ${command.srcAccountId} не предназначен")
            }
            produceCommand(FeeRequest(command.srcAccountId, command.money))

            Future.successful()
        }
        .to(Sink.ignore)
        .run()
}
