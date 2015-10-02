package com.besuikerd.form

trait FormElement[A] {
  val identifier: String

  val formGenerator: FormGenerator


  def map[B](f: A => B): FormElement[B] = new FormElementMapped(this, f)
  def flatMap[B](f: A => FormElement[B]) = f(result)
  //def filter(f: A => Boolean, errorMessage: String): FormElement[A]

}

class FormElementMapped[A, B](wrapped: FormElement[A], transformer: A => B) extends FormElement[B]{
  override val identifier: String = wrapped.identifier
  override val result = transformer(wrapped.result)

  override def toString: String = s"Mapped($wrapped)"
}

class FormElementFlatMapped[A,B](wrapped: FormElement[A], f: A => FormElement[B]) extends FormElement[B]{
  override val identifier: String = wrapped.identifier
  override val result = f(wrapped.result).result
}

trait FormGenerator{

}


//class FormElementMapped[A, B](wrapped: FormElement[A], f: A => B) extends FormElement[B]{
//  val result = wrapped.result.right.map(f)
//}

object FormElement{
  def inputText(s:String) = FormElementInputText(s)

  case class FormElementInputText(s : String) extends FormElement[String]{
  }
}