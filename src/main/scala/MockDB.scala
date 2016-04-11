import MyData.User

trait DB {
  def createUser(name: String): Either[Throwable, User]
  def getUsers: Either[Throwable, Seq[User]]
  def getUser(id: Int): Either[Throwable, Option[User]]
}

object MockDB extends DB {
  private var user_table = Seq[User]()
  private var next_id = 0
  private def nextId() = {
    next_id += 1
    next_id
  }

  override def createUser(name: String) = {
    val user = User(nextId(), name)
    user_table = user_table :+ user
    Right(user)
  }
  override def getUsers = Right(user_table)
  override def getUser(id: Int) = Right(user_table.find(_.id == id))
}