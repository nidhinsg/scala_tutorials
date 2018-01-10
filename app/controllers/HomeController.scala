package controllers

import javax.inject.Inject
import models._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(todoService: TodoList, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  val todoForm = Form(
    "label" -> nonEmptyText
  )

  def todolist() = Action { implicit request =>
    Ok(views.html.todolist(todoService.all(), todoForm))
  }

  def createlist() = Action { implicit request =>
    todoForm.bindFromRequest.fold(
      errors => BadRequest(views.html.todolist(todoService.all(), errors)),
      label => {
        todoService.create(label)
        Redirect(routes.HomeController.todolist)
      }
    ) 
  }

  def delete(id: Long) = Action {
    todoService.delete(id)
    Redirect(routes.HomeController.todolist)
  }
}
