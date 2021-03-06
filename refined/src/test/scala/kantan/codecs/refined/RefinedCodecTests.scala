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

package kantan.codecs.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Positive
import kantan.codecs.laws.discipline.{CodecTests, DecoderTests, EncoderTests, SerializableTests}
import kantan.codecs.refined.laws.discipline.arbitrary._
import kantan.codecs.strings._
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline

class RefinedCodecTests extends FunSuite with GeneratorDrivenPropertyChecks with Discipline {
  checkAll(
    "StringDecoder[Int Refined Positive]",
    DecoderTests[String, Int Refined Positive, DecodeError, codecs.type].decoder[Int, Int]
  )
  checkAll("StringDecoder[Int Refined Positive]", SerializableTests[StringEncoder[Int Refined Positive]].serializable)

  checkAll(
    "StringEncoder[Int Refined Positive]",
    EncoderTests[String, Int Refined Positive, codecs.type].encoder[Int, Int]
  )
  checkAll("StringEncoder[Int Refined Positive]", SerializableTests[StringEncoder[Int Refined Positive]].serializable)

  checkAll(
    "StringCodec[Int Refined Positive]",
    CodecTests[String, Int Refined Positive, DecodeError, codecs.type].codec[Int, Int]
  )
}
