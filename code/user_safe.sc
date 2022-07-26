//> using scala "3.1.2"

type IO[A] = A // should be an actual IO-like type

case class Email(value: String)
case class ID(value: Long)

enum User:
  case New(id: ID)
  case Registered(id: ID,
                  email: Email)

import User._

def notifyUser(user: User): IO[Unit] =
  user match
    case Registered(id, email) => sendToEmail(email)
    case New(id) => skippedNotification(id)
  
def sendToEmail(email: Email): IO[Unit] = 
  println(s"Notifying: $email")
  
def skippedNotification(id: ID): IO[Unit] =
  println(s"Skipped notifying $id")

val user1 = User.Registered(
  id = ID(123), 
  email = Email("a@a.com"))

val user2 = User.New(id = ID(456))
  
notifyUser(user1)
notifyUser(user2)

// val user3 = User.Registered(
//   id = ID(123), 
//   email = Option.empty[Email])
