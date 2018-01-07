import MyData.User

import scalikejdbc._

trait DB {
  def createUser(name: String): Either[Throwable, User]
  def getUsers: Either[Throwable, Seq[User]]
  def getUser(id: Int): Either[Throwable, Option[User]]
  def updateUser(id: Int, name: String): Either[Throwable, Option[User]]
  def deleteUser(id: Int): Either[Throwable, Unit]
}

object H2DB extends DB {

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")

  implicit val session = AutoSession
  sql"""
       CREATE TABLE IF NOT EXISTS USERS (
         ID BIGINT NOT NULL AUTO_INCREMENT,
         NAME VARCHAR(100 CHAR) NOT NULL,
         PRIMARY KEY (ID)
       )
    """.execute().apply()

  def createUser(name: String): Either[Throwable, User] = {
    val id = sql"insert into USERS(NAME) values (${name})"
      .updateAndReturnGeneratedKey.apply().toInt
    val user = User(id, name)
    Right(user)
  }

  def getUsers: Either[Throwable, Seq[User]] = {
    val users = sql"select ID, NAME from USERS".map { rs =>
      User(rs.long("ID").toInt,rs.string("NAME"))
    }.list().apply()
    Right(users)
  }

  def getUser(id: Int): Either[Throwable, Option[User]] = {
    val user = sql"select ID, NAME from USERS where ID = ${id}".map { rs =>
      User(rs.long("ID").toInt,rs.string("NAME"))
    }.single().apply()
    Right(user)
  }

  def updateUser(id: Int, name: String): Either[Throwable, Option[User]] = {
    sql"update USER NAME = ${name} where ID = ${id}".update().apply()
    Right(Option(User(id, name)))
  }

  def deleteUser(id: Int): Either[Throwable, Unit] = {
    sql"delete USER where ID=${id}".update().apply()
    Right(())
  }
}

object MockDB extends DB {
  private var user_table = Seq[User]()
  private var next_user_id = 0
  private def nextId() = {
    next_user_id += 1
    next_user_id
  }

  def createUser(name: String) = {
    val user = User(nextId(), name)
    user_table = user_table :+ user
    Right(user)
  }

  def getUsers = Right(user_table)

  def getUser(id: Int) = Right(user_table.find(_.id == id))

  def updateUser(id: Int, name: String) = {
    user_table = user_table.map(u =>
      if (u.id == id) u.copy(name = name)
      else u
    )
    getUser(id)
  }

  def deleteUser(id: Int) = {
    user_table = user_table.filterNot(_.id == id)
    Right(())
  }
}
