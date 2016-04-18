import MyData.User

trait DB {
  def createUser(name: String): Either[Throwable, User]
  def getUsers: Either[Throwable, Seq[User]]
  def getUser(id: Int): Either[Throwable, Option[User]]
  def updateUser(id: Int, name: String): Either[Throwable, Option[User]]
  def deleteUser(id: Int): Either[Throwable, Unit]
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
