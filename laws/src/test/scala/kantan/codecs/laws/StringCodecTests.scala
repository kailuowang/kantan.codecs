package kantan.codecs.laws

import kantan.codecs.laws.discipline.{DecoderTests, EncoderTests}
import kantan.codecs.{DecodeResult, Decoder, Encoder}
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline

class StringCodecTests extends FunSuite with GeneratorDrivenPropertyChecks with Discipline {
  implicit val stringDecoder = Decoder[String, String, Boolean](s ⇒ DecodeResult.success(s))
  implicit val stringEncoder = Encoder[String, String](s ⇒ s)

  checkAll("Decoder[String, String, Boolean]", DecoderTests[String, String, Boolean].bijectiveDecoder[Int, Int])
  checkAll("Encoder[String, String]", EncoderTests[String, String].bijectiveEncoder[Int, Int])
}

