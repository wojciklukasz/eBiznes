package v1.product

import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext
import play.api.{Logger, MarkerContext}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

final case class ProductData(id: ProductId, name: String, body: String)

class ProductId private (val underlying: Int) extends AnyVal {
  override def toString: String = underlying.toString
}

object ProductId {
  def apply(raw: String): ProductId = {
    require(raw != null)
    new ProductId(Integer.parseInt(raw))
  }
}

class ProductExecutionContext @Inject()(actorSystem: ActorSystem)
    extends CustomExecutionContext(actorSystem, "repository.dispatcher")

/**
  * A pure non-blocking interface for the ProductRepository.
  */
trait ProductRepository {
  def create(data: ProductData)(implicit mc: MarkerContext): Future[ProductId]

  def list()(implicit mc: MarkerContext): Future[Iterable[ProductData]]

  def get(id: ProductId)(implicit mc: MarkerContext): Future[Option[ProductData]]

  def delete(id: String)(implicit mc: MarkerContext): Future[Option[ProductData]]

  def update(id: String, data: ProductData)(implicit mc: MarkerContext): Future[Option[ProductData]]
}

/**
  * A trivial implementation for the Product Repository.
  *
  * A custom execution context is used here to establish that blocking operations should be
  * executed in a different thread than Play's ExecutionContext, which is used for CPU bound tasks
  * such as rendering.
  */
@Singleton
class ProductRepositoryImpl @Inject()()(implicit ec: ProductExecutionContext)
    extends ProductRepository {

  private val logger = Logger(this.getClass)

  private val productList = ListBuffer(
    ProductData(ProductId("1"), "name 1", "product 1"),
    ProductData(ProductId("2"), "name 2", "product 2"),
    ProductData(ProductId("3"), "name 3", "product 3"),
    ProductData(ProductId("4"), "name 4", "product 4"),
    ProductData(ProductId("5"), "name 5", "product 5")
  )

  override def list()(
      implicit mc: MarkerContext): Future[Iterable[ProductData]] = {
    Future {
      logger.trace(s"list: ")
      productList
    }
  }

  override def get(id: ProductId)(
      implicit mc: MarkerContext): Future[Option[ProductData]] = {
    Future {
      logger.trace(s"get: id = $id")
      productList.find(product => product.id == id)
    }
  }

  def create(data: ProductData)(implicit mc: MarkerContext): Future[ProductId] = {
    Future {
      logger.trace(s"create: data = $data")
      data.id
    }
  }

  override def delete(id: String)(implicit mc: MarkerContext): Future[Option[ProductData]] = {
    Future {
      logger.trace(s"delete: id = $id")
      val deleted = productList.find(product => product.id == ProductId(id))
      productList.remove(productList.indexWhere(product => product.id == ProductId(id)))
      deleted
    }
  }

  override def update(id: String, data: ProductData)(implicit mc: MarkerContext): Future[Option[ProductData]] = {
    Future {
      logger.trace(s"update: id = $id")
      logger.trace(productList.indexWhere(product => product.id == ProductId(id)).toString)
      productList.update(productList.indexWhere(product => product.id == ProductId(id)), data)
      productList.find(product => product.id == ProductId(id))
    }
  }

}
