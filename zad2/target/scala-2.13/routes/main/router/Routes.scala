// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:1
  v1_post_PostRouter_0: v1.post.PostRouter,
  // @LINE:2
  v1_product_ProductRouter_1: v1.product.ProductRouter,
  // @LINE:3
  v1_category_CategoryRouter_2: v1.category.CategoryRouter,
  // @LINE:4
  v1_user_UserRouter_3: v1.user.UserRouter,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:1
    v1_post_PostRouter_0: v1.post.PostRouter,
    // @LINE:2
    v1_product_ProductRouter_1: v1.product.ProductRouter,
    // @LINE:3
    v1_category_CategoryRouter_2: v1.category.CategoryRouter,
    // @LINE:4
    v1_user_UserRouter_3: v1.user.UserRouter
  ) = this(errorHandler, v1_post_PostRouter_0, v1_product_ProductRouter_1, v1_category_CategoryRouter_2, v1_user_UserRouter_3, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, v1_post_PostRouter_0, v1_product_ProductRouter_1, v1_category_CategoryRouter_2, v1_user_UserRouter_3, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    prefixed_v1_post_PostRouter_0_0.router.documentation,
    prefixed_v1_product_ProductRouter_1_1.router.documentation,
    prefixed_v1_category_CategoryRouter_2_2.router.documentation,
    prefixed_v1_user_UserRouter_3_3.router.documentation,
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:1
  private[this] val prefixed_v1_post_PostRouter_0_0 = Include(v1_post_PostRouter_0.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "v1/posts"))

  // @LINE:2
  private[this] val prefixed_v1_product_ProductRouter_1_1 = Include(v1_product_ProductRouter_1.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "vi/products"))

  // @LINE:3
  private[this] val prefixed_v1_category_CategoryRouter_2_2 = Include(v1_category_CategoryRouter_2.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "v1/categories"))

  // @LINE:4
  private[this] val prefixed_v1_user_UserRouter_3_3 = Include(v1_user_UserRouter_3.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "v1/users"))


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:1
    case prefixed_v1_post_PostRouter_0_0(handler) => handler
  
    // @LINE:2
    case prefixed_v1_product_ProductRouter_1_1(handler) => handler
  
    // @LINE:3
    case prefixed_v1_category_CategoryRouter_2_2(handler) => handler
  
    // @LINE:4
    case prefixed_v1_user_UserRouter_3_3(handler) => handler
  }
}
