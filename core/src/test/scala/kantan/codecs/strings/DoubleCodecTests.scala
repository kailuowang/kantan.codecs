/*
 * Copyright 2016 Nicolas Rinaudo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kantan.codecs.strings

import kantan.codecs.laws.discipline._
import kantan.codecs.laws.discipline.arbitrary._
import kantan.codecs.strings.tagged._
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline

class DoubleCodecTests extends FunSuite with GeneratorDrivenPropertyChecks with Discipline {
  checkAll("StringDecoder[Double]", DecoderTests[String, Double, DecodeError, codecs.type].decoder[Int, Int])
  checkAll("StringDecoder[Double]", SerializableTests[StringDecoder[Double]].serializable)

  checkAll("StringEncoder[Double]", EncoderTests[String, Double, codecs.type].encoder[Int, Int])
  checkAll("StringEncoder[Double]", SerializableTests[StringEncoder[Double]].serializable)

  checkAll("StringCodec[Double]", CodecTests[String, Double, DecodeError, codecs.type].codec[Int, Int])

  checkAll("TaggedDecoder[Double]", DecoderTests[String, Double, DecodeError, tagged.type].decoder[Int, Int])
  checkAll("TaggedEncoder[Double]", EncoderTests[String, Double, tagged.type].encoder[Int, Int])
}
