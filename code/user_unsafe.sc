//> using scala "3.1.2"

type IO[A] = A // should be an actual IO-like type

case class Email(value: String)
case class ID(value: Long)

case class User(id: ID, 
                registered: Boolean,
                email: Option[Email])

def notifyUser(user: User): IO[Unit] =
  if user.registered then
    // this is SAFE
    // registered users ALWAYS have an email
    val email = user.email.get
    
    sendToEmail(email)
  
def sendToEmail(email: Email): IO[Unit] = 
  println(s"Notifying: $email")

val user1 = User(
  id = ID(123), 
  registered = true,
  email = Some(Email("a@a.com")))
  
val user2 = User(
  id = ID(456), 
  registered = true,
  email = None)
  
notifyUser(user1)
notifyUser(user2)
