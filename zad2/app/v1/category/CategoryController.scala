package v1.category

import javax.inject.Inject

import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

case class CategoryFormInput(title: String, body: String)

/**
  * Takes HTTP requests and produces JSON.
  */
class CategoryController @Inject()(cc: CategoryControllerComponents)(
    implicit ec: ExecutionContext)
    extends CategoryBaseController(cc) {

  private val logger = Logger(getClass)

  private val form: Form[CategoryFormInput] = {
    import play.api.data.Forms._

    Form(
      mapping(
        "name" -> nonEmptyText,
        "body" -> text
      )(CategoryFormInput.apply)(CategoryFormInput.unapply)
    )
  }

  def index: Action[AnyContent] = CategoryAction.async { implicit request =>
    logger.trace("index: ")
    categoryResourceHandler.find.map { categorys =>
      Ok(Json.toJson(categorys))
    }
  }

  def process: Action[AnyContent] = CategoryAction.async { implicit request =>
    logger.trace("process: ")
    processJsonCategory()
  }

  def show(id: String): Action[AnyContent] = CategoryAction.async {
    implicit request =>
      logger.trace(s"show: id = $id")
      categoryResourceHandler.lookup(id).map { category =>
        Ok(Json.toJson(category))
      }
  }

  def delete(id: String): Action[AnyContent] = CategoryAction.async {
    implicit request =>
      logger.trace(s"deleting: id = $id")
      categoryResourceHandler.delete(id).map { category =>
        Ok(Json.toJson(category))
      }
  }

  def update(id: String): Action[AnyContent] = CategoryAction.async {
    implicit request =>
      logger.trace(s"updating: id = $id")
      processJsonUpdate(id)
  }

  private def processJsonCategory[A]()(
      implicit request: CategoryRequest[A]): Future[Result] = {
    def failure(badForm: Form[CategoryFormInput]) = {
      Future.successful(BadRequest(badForm.errorsAsJson))
    }

    def success(input: CategoryFormInput) = {
      categoryResourceHandler.create(input).map { category =>
        Created(Json.toJson(category)).withHeaders(LOCATION -> category.link)
      }
    }

    form.bindFromRequest().fold(failure, success)
  }

  private def processJsonUpdate[A](id: String)(
      implicit request: CategoryRequest[A]): Future[Result] = {
    def failure(badForm: Form[CategoryFormInput]) = {
      Future.successful(BadRequest(badForm.errorsAsJson))
    }

    def success(input: CategoryFormInput) = {
      categoryResourceHandler.update(id, input)
      categoryResourceHandler.lookup(id).map { category =>
        Ok(Json.toJson(category))
      }
    }

    form.bindFromRequest().fold(failure, success)
  }

}
