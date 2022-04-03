package v1.product

import javax.inject.{Inject, Provider}

import play.api.MarkerContext

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._

/**
  * DTO for displaying product information.
  */
case class ProductResource(id: String, link: String, name: String, body: String)

object ProductResource {
  /**
    * Mapping to read/write a ProductResource out as a JSON value.
    */
    implicit val format: Format[ProductResource] = Json.format
}


/**
  * Controls access to the backend data, returning [[ProductResource]]
  */
class ProductResourceHandler @Inject()(
    routerProvider: Provider[ProductRouter],
    productRepository: ProductRepository)(implicit ec: ExecutionContext) {

  def create(productInput: ProductFormInput)(
      implicit mc: MarkerContext): Future[ProductResource] = {
    val data = ProductData(ProductId("999"), productInput.title, productInput.body)
    // We don't actually create the product, so return what we have
    productRepository.create(data).map { id =>
      createProductResource(data)
    }
  }

  def lookup(id: String)(
      implicit mc: MarkerContext): Future[Option[ProductResource]] = {
    val productFuture = productRepository.get(ProductId(id))
    productFuture.map { maybeProductData =>
      maybeProductData.map { productData =>
        createProductResource(productData)
      }
    }
  }

  def find(implicit mc: MarkerContext): Future[Iterable[ProductResource]] = {
    productRepository.list().map { productDataList =>
      productDataList.map(productData => createProductResource(productData))
    }
  }

  def delete(id: String)(implicit mc: MarkerContext): Future[Option[ProductResource]] = {
    productRepository.delete(id).map { productDataList =>
      productDataList.map(productData => createProductResource(productData))
    }
  }

  def update(id: String, productInput: ProductFormInput)(implicit mc: MarkerContext): Future[Option[ProductData]] = {
    val data = ProductData(ProductId(id), productInput.title, productInput.body)
    productRepository.update(id, data)
  }

  private def createProductResource(p: ProductData): ProductResource = {
    ProductResource(p.id.toString, routerProvider.get.link(p.id), p.name, p.body)
  }

}
