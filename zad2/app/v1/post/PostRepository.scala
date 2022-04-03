package v1.post

import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext
import play.api.{Logger, MarkerContext}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

final case class PostData(id: PostId, title: String, body: String)

class PostId private (val underlying: Int) extends AnyVal {
  override def toString: String = underlying.toString
}

object PostId {
  def apply(raw: String): PostId = {
    require(raw != null)
    new PostId(Integer.parseInt(raw))
  }
}

class PostExecutionContext @Inject()(actorSystem: ActorSystem)
    extends CustomExecutionContext(actorSystem, "repository.dispatcher")

/**
  * A pure non-blocking interface for the PostRepository.
  */
trait PostRepository {
  def create(data: PostData)(implicit mc: MarkerContext): Future[PostId]

  def list()(implicit mc: MarkerContext): Future[Iterable[PostData]]

  def get(id: PostId)(implicit mc: MarkerContext): Future[Option[PostData]]

  def delete(id: String)(implicit mc: MarkerContext): Future[Option[PostData]]

  def update(id: String, data: PostData)(implicit mc: MarkerContext): Future[Option[PostData]]
}

/**
  * A trivial implementation for the Post Repository.
  *
  * A custom execution context is used here to establish that blocking operations should be
  * executed in a different thread than Play's ExecutionContext, which is used for CPU bound tasks
  * such as rendering.
  */
@Singleton
class PostRepositoryImpl @Inject()()(implicit ec: PostExecutionContext)
    extends PostRepository {

  private val logger = Logger(this.getClass)

  private val postList = ListBuffer(
    PostData(PostId("1"), "title 1", "blog post 1"),
    PostData(PostId("2"), "title 2", "blog post 2"),
    PostData(PostId("3"), "title 3", "blog post 3"),
    PostData(PostId("4"), "title 4", "blog post 4"),
    PostData(PostId("5"), "title 5", "blog post 5")
  )

  override def list()(
      implicit mc: MarkerContext): Future[Iterable[PostData]] = {
    Future {
      logger.trace(s"list: ")
      postList
    }
  }

  override def get(id: PostId)(
      implicit mc: MarkerContext): Future[Option[PostData]] = {
    Future {
      logger.trace(s"get: id = $id")
      postList.find(post => post.id == id)
    }
  }

  def create(data: PostData)(implicit mc: MarkerContext): Future[PostId] = {
    Future {
      logger.trace(s"create: data = $data")
      data.id
    }
  }

  override def delete(id: String)(implicit mc: MarkerContext): Future[Option[PostData]] = {
    Future {
      logger.trace(s"delete: id = $id")
      val deleted = postList.find(post => post.id == PostId(id))
      postList.remove(postList.indexWhere(post => post.id == PostId(id)))
      deleted
    }
  }

  override def update(id: String, data: PostData)(implicit mc: MarkerContext): Future[Option[PostData]] = {
    Future {
      logger.trace(s"update: id = $id")
      logger.trace(postList.indexWhere(post => post.id == PostId(id)).toString)
      postList.update(postList.indexWhere(post => post.id == PostId(id)), data)
      postList.find(post => post.id == PostId(id))
    }
  }

}
