package models

import java.util.Date
import javax.inject.Inject

import anorm.SqlParser._
import anorm._
import play.api.db.DBApi

import scala.concurrent.Future

case class Todo (id: Long, label: String)

@javax.inject.Singleton
class TodoList @Inject()(dbapi: DBApi)(implicit ec: DatabaseExecutionContext) {

	private val db = dbapi.database("default")

	val todo = {
	  get[Long]("id") ~ 
	  get[String]("label") map {
	    case id~label => Todo(id, label)
	  }
	}

  def all(): List[Todo] = db.withConnection { implicit c =>
	  SQL("select * from todo").as(todo *)
	}

	def create(label: String) {
		db.withConnection { implicit c =>
			SQL("insert into todo (label) values ({label})").on(
				'label -> label
			).executeUpdate()
		}
	}

	def delete(id: Long) {
		db.withConnection { implicit c =>
			SQL("delete from todo where id = {id}").on(
				'id -> id
			).executeUpdate()
		}
	}
  
}