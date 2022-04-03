package v1.category

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * Routes and URLs to the CategoryResource controller.
  */
class CategoryRouter @Inject()(controller: CategoryController) extends SimpleRouter {
  val prefix = "/v1/categorys"

  def link(id: CategoryId): String = {
    import io.lemonlabs.uri.dsl._
    val url = prefix / id.toString
    url.toString()
  }

  override def routes: Routes = {
    case GET(p"/") =>
      controller.index

    case POST(p"/") =>
      controller.process

    case GET(p"/$id") =>
      controller.show(id)

    case DELETE(p"/$id") =>
      controller.delete(id)

    case PUT(p"/$id") =>
      controller.update(id)
  }

}
