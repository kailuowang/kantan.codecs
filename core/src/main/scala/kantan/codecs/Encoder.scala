package kantan.codecs

/** Type class for types that can be encoded into others.
  *
  * @tparam E encoded type - what to encode to.
  * @tparam D decoded type - what to encode from.
  * @tparam T tag type.
  */
trait Encoder[E, D, T] extends Any with Serializable {
  /** Encodes the specified value. */
  def encode(d: D): E

  def mapEncoded[EE](f: E ⇒ EE): Encoder[EE, D, T] = Encoder(d ⇒ f(encode(d)))

  /** Creates a new [[Encoder]] instances that applies the specified function before encoding.
    *
    * This is a convenient way of creating [[Encoder]] instances: if you already have an `Encoder[E, D, R]`, need to
    * write an `Encoder[E, DD, R]` and know how to turn a `DD` into a `D`, you need but call [[contramap]].
    */
  def contramap[DD](f: DD ⇒ D): Encoder[E, DD, T] = Encoder(f andThen encode)

  def tag[TT]: Encoder[E, D, TT] = this.asInstanceOf[Encoder[E, D, TT]]
}

object Encoder {
  def apply[E, D, T](f: D ⇒ E): Encoder[E, D, T] = new Encoder[E, D, T] {
    override def encode(d: D) = f(d)
  }
}