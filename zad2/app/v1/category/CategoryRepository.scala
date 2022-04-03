package v1.category

import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext
import play.api.{Logger, MarkerContext}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

final case class CategoryData(id: CategoryId, name: String, body: String)

class CategoryId private (val underlying: Int) extends AnyVal {
  override def toString: String = underlying.toString
}

object CategoryId {
  def apply(raw: String): CategoryId = {
    require(raw != null)
    new CategoryId(Integer.parseInt(raw))
  }
}

class CategoryExecutionContext @Inject()(actorSystem: ActorSystem)
    extends CustomExecutionContext(actorSystem, "repository.dispatcher")

/**
  * A pure non-blocking interface for the CategoryRepository.
  */
trait CategoryRepository {
  def create(data: CategoryData)(implicit mc: MarkerContext): Future[CategoryId]

  def list()(implicit mc: MarkerContext): Future[Iterable[CategoryData]]

  def get(id: CategoryId)(implicit mc: MarkerContext): Future[Option[CategoryData]]

  def delete(id: String)(implicit mc: MarkerContext): Future[Option[CategoryData]]

  def update(id: String, data: CategoryData)(implicit mc: MarkerContext): Future[Option[CategoryData]]
}

/**
  * A trivial implementation for the Category Repository.
  *
  * A custom execution context is used here to establish that blocking operations should be
  * executed in a different thread than Play's ExecutionContext, which is used for CPU bound tasks
  * such as rendering.
  */
@Singleton
class CategoryRepositoryImpl @Inject()()(implicit ec: CategoryExecutionContext)
    extends CategoryRepository {

  private val logger = Logger(this.getClass)

  private val categoryList = ListBuffer(
    CategoryData(CategoryId("1"), "name 1", "category 1"),
    CategoryData(CategoryId("2"), "name 2", "category 2"),
    CategoryData(CategoryId("3"), "name 3", "category 3"),
    CategoryData(CategoryId("4"), "name 4", "category 4"),
    CategoryData(CategoryId("5"), "name 5", "category 5")
  )

  override def list()(
      implicit mc: MarkerContext): Future[Iterable[CategoryData]] = {
    Future {
      logger.trace(s"list: ")
      categoryList
    }
  }

  override def get(id: CategoryId)(
      implicit mc: MarkerContext): Future[Option[CategoryData]] = {
    Future {
      logger.trace(s"get: id = $id")
      categoryList.find(category => category.id == id)
    }
  }

  def create(data: CategoryData)(implicit mc: MarkerContext): Future[CategoryId] = {
    Future {
      logger.trace(s"create: data = $data")
      data.id
    }
  }

  override def delete(id: String)(implicit mc: MarkerContext): Future[Option[CategoryData]] = {
    Future {
      logger.trace(s"delete: id = $id")
      val deleted = categoryList.find(category => category.id == CategoryId(id))
      categoryList.remove(categoryList.indexWhere(category => category.id == CategoryId(id)))
      deleted
    }
  }

  override def update(id: String, data: CategoryData)(implicit mc: MarkerContext): Future[Option[CategoryData]] = {
    Future {
      logger.trace(s"update: id = $id")
      logger.trace(categoryList.indexWhere(category => category.id == CategoryId(id)).toString)
      categoryList.update(categoryList.indexWhere(category => category.id == CategoryId(id)), data)
      categoryList.find(category => category.id == CategoryId(id))
    }
  }

}
