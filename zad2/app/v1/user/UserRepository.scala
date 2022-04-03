package v1.user

import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext
import play.api.{Logger, MarkerContext}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

final case class UserData(id: UserId, username: String, password: String)

class UserId private (val underlying: Int) extends AnyVal {
  override def toString: String = underlying.toString
}

object UserId {
  def apply(raw: String): UserId = {
    require(raw != null)
    new UserId(Integer.parseInt(raw))
  }
}

class UserExecutionContext @Inject()(actorSystem: ActorSystem)
    extends CustomExecutionContext(actorSystem, "repository.dispatcher")

/**
  * A pure non-blocking interface for the UserRepository.
  */
trait UserRepository {
  def create(data: UserData)(implicit mc: MarkerContext): Future[UserId]

  def list()(implicit mc: MarkerContext): Future[Iterable[UserData]]

  def get(id: UserId)(implicit mc: MarkerContext): Future[Option[UserData]]

  def delete(id: String)(implicit mc: MarkerContext): Future[Option[UserData]]

  def update(id: String, data: UserData)(implicit mc: MarkerContext): Future[Option[UserData]]
}

/**
  * A trivial implementation for the User Repository.
  *
  * A custom execution context is used here to establish that blocking operations should be
  * executed in a different thread than Play's ExecutionContext, which is used for CPU bound tasks
  * such as rendering.
  */
@Singleton
class UserRepositoryImpl @Inject()()(implicit ec: UserExecutionContext)
    extends UserRepository {

  private val logger = Logger(this.getClass)

  private val userList = ListBuffer(
    UserData(UserId("1"), "username 1", "password 1"),
    UserData(UserId("2"), "username 2", "password 2"),
    UserData(UserId("3"), "username 3", "password 3"),
    UserData(UserId("4"), "username 4", "password 4"),
    UserData(UserId("5"), "username 5", "password 5")
  )

  override def list()(
      implicit mc: MarkerContext): Future[Iterable[UserData]] = {
    Future {
      logger.trace(s"list: ")
      userList
    }
  }

  override def get(id: UserId)(
      implicit mc: MarkerContext): Future[Option[UserData]] = {
    Future {
      logger.trace(s"get: id = $id")
      userList.find(user => user.id == id)
    }
  }

  def create(data: UserData)(implicit mc: MarkerContext): Future[UserId] = {
    Future {
      logger.trace(s"create: data = $data")
      data.id
    }
  }

  override def delete(id: String)(implicit mc: MarkerContext): Future[Option[UserData]] = {
    Future {
      logger.trace(s"delete: id = $id")
      val deleted = userList.find(user => user.id == UserId(id))
      userList.remove(userList.indexWhere(user => user.id == UserId(id)))
      deleted
    }
  }

  override def update(id: String, data: UserData)(implicit mc: MarkerContext): Future[Option[UserData]] = {
    Future {
      logger.trace(s"update: id = $id")
      logger.trace(userList.indexWhere(user => user.id == UserId(id)).toString)
      userList.update(userList.indexWhere(user => user.id == UserId(id)), data)
      userList.find(user => user.id == UserId(id))
    }
  }

}
