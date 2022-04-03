package v1.category

import javax.inject.{Inject, Provider}

import play.api.MarkerContext

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._

/**
  * DTO for displaying category information.
  */
case class CategoryResource(id: String, link: String, name: String, body: String)

object CategoryResource {
  /**
    * Mapping to read/write a CategoryResource out as a JSON value.
    */
    implicit val format: Format[CategoryResource] = Json.format
}


/**
  * Controls access to the backend data, returning [[CategoryResource]]
  */
class CategoryResourceHandler @Inject()(
    routerProvider: Provider[CategoryRouter],
    categoryRepository: CategoryRepository)(implicit ec: ExecutionContext) {

  def create(categoryInput: CategoryFormInput)(
      implicit mc: MarkerContext): Future[CategoryResource] = {
    val data = CategoryData(CategoryId("999"), categoryInput.title, categoryInput.body)
    // We don't actually create the category, so return what we have
    categoryRepository.create(data).map { id =>
      createCategoryResource(data)
    }
  }

  def lookup(id: String)(
      implicit mc: MarkerContext): Future[Option[CategoryResource]] = {
    val categoryFuture = categoryRepository.get(CategoryId(id))
    categoryFuture.map { maybeCategoryData =>
      maybeCategoryData.map { categoryData =>
        createCategoryResource(categoryData)
      }
    }
  }

  def find(implicit mc: MarkerContext): Future[Iterable[CategoryResource]] = {
    categoryRepository.list().map { categoryDataList =>
      categoryDataList.map(categoryData => createCategoryResource(categoryData))
    }
  }

  def delete(id: String)(implicit mc: MarkerContext): Future[Option[CategoryResource]] = {
    categoryRepository.delete(id).map { categoryDataList =>
      categoryDataList.map(categoryData => createCategoryResource(categoryData))
    }
  }

  def update(id: String, categoryInput: CategoryFormInput)(implicit mc: MarkerContext): Future[Option[CategoryData]] = {
    val data = CategoryData(CategoryId(id), categoryInput.title, categoryInput.body)
    categoryRepository.update(id, data)
  }

  private def createCategoryResource(p: CategoryData): CategoryResource = {
    CategoryResource(p.id.toString, routerProvider.get.link(p.id), p.name, p.body)
  }

}
