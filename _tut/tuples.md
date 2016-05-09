---
layout: tutorial
title: "Extracting tuples"
section: tutorial
sort: 2
---
We've seen in a [previous tutorial](primitive_types.html) how to extract simple types from matches in a regular
expression. Sometimes, however, matches contain more than one interesting value, each in a separate group.

For example, consider the following string:

```scala
val input = "[1, 2] and then [3, 4] followed by [5, 6, 7]"
```

We might want to extract all the parts that look like a point from it - this could be achieved with a simple regular
expression, something like:

```scala
scala> val regex = "\\[(\\d+), (\\d+)\\]"
regex: String = \[(\d+), (\d+)\]
```

Note how the "interesting" parts are each in their own group, this is critical to kantan.regex behaving properly. 

First, we'll need to import the kantan.regex syntax:

```scala
import kantan.regex.ops._
```

We can then proceed to extract our points as `(Int, Int)` exactly like we did before for simple types, through
[`evalRegex`]:

```scala
scala> input.evalRegex[(Int, Int)](regex).foreach(println _)
Success((1,2))
Success((3,4))
```

Note that this will map the each group in a match to the corresponding field in a tuple. If your groups and tuple
values are not in the same order, you need a bit [more legwork](case_classes.html).

This is not entirely satisfactory, though: if you take another look at our `input` string, you'll see that we have a
third point, this one with a `z` coordinate.

The following regex can be used to match the first two coordinates with an optional third:

```scala
scala> val regex = "\\[(\\d+), (\\d+)(?:, (\\d+))?\\]"
regex: String = \[(\d+), (\d+)(?:, (\d+))?\]
```

One way of interpreting matches from this regex would be as `(Int, Int, Option[Int])`: triples with two ints and an
optional third. This is achieved exactly as you'd expect:

```scala
scala> input.evalRegex[(Int, Int, Option[Int])](regex).foreach(println _)
Success((1,2,None))
Success((3,4,None))
Success((5,6,Some(7)))
```

Another way would be as either an `(Int, Int, Int)` or an `(Int, Int)`, which is also as simple as specifying the
right type parameter to [`evalRegex`]:

```scala
scala> input.evalRegex[Either[(Int, Int, Int), (Int, Int)]](regex).foreach(println _)
Success(Right((1,2)))
Success(Right((3,4)))
Success(Left((5,6,7)))
```

Note, however, that there's a small catch when decoding to [`Either`]: the most discriminatory type should always go
on the left. The reason for this is that [`Either`] will first attempt to decode as the left type, and stop there if
successful.  If we'd swapped the type parameter in our previous example, we'd not have gotten quite what we wanted:

```scala
scala> input.evalRegex[Either[(Int, Int), (Int, Int, Int)]](regex).foreach(println _)
Success(Left((1,2)))
Success(Left((3,4)))
Success(Left((5,6)))
```

[`evalRegex`]:{{ site.baseUrl }}/api/index.html#kantan.regex.ops$$StringOps@evalRegex[A](expr:String,group:Int)(implicitevidence$2:kantan.regex.GroupDecoder[A]):Iterator[kantan.regex.RegexResult[A]]
[`Either`]:http://www.scala-lang.org/api/current/index.html#scala.util.Either
