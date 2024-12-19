package fulltext.search.engine.engine

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.nio.file.Files
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.IntField
import org.apache.lucene.document.TextField
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IndexSearchOneTermTest {

    private lateinit var index: Index

    @BeforeAll
    fun testCreateAndFullIndex(): Unit {
        val path = Files.createTempDirectory("test123")
        index = Index(path)

        val doc1 = Document()
        doc1.add(Field("Id", "1", TextField.TYPE_STORED))
        doc1.add(Field("Name", "The Art of Unit Testing", TextField.TYPE_STORED))
        doc1.add(Field("Author", "ROY OSHEROVE", TextField.TYPE_STORED))
        doc1.add(Field("Content", """
            foreword to the second edition

            The year must have been 2009. I was speaking at the Norwegian Developers Confer-
            ence in Oslo. (Ah, Oslo in June!) The event was held in a huge sports arena. The con-
            ference organizers divided the bleachers into sections, built stages in front of them,

            and draped them with thick black cloth in order to create eight different session
            “rooms.” I remember I was just about finished with my talk, which was about TDD, or
            SOLID, or astronomy, or something, when suddenly, from the stage next to me, came
            this loud and raucous singing and guitar playing.
            The drapes were such that I was able to peer around them and see the fellow on
            the stage next to mine, who was making all the noise. Of course, it was Roy Osherove.
            Now, those of you who know me know that breaking into song in the middle of a
            technical talk about software is something that I might just do, if the mood struck me.
            So as I turned back to my audience, I thought to myself that this Osherove fellow was a
            kindred spirit, and I’d have to get to know him better.
            And getting to know him better is just what I did. In fact, he made a significant

            contribution to my most recent book The Clean Coder and spent three days with me co-
            teaching a TDD class. My experiences with Roy have all been very positive, and I hope

            there are many more.
            I predict that your experience with Roy, in the reading of this book, will be very
            positive as well because this book is something special.
            Have you ever read a Michener novel? I haven’t; but I’ve been told that they all
            start at “the atom.” The book you’re holding isn’t a James Michener novel, but it does
            start at the atom—the atom of unit testing.
        """.trimIndent(), TextField.TYPE_NOT_STORED))
        doc1.add(IntField("Page", 15, Field.Store.YES))
        index.addDocument(doc1)

        val doc2 = Document()
        doc2.add(Field("Id", "2", TextField.TYPE_STORED))
        doc2.add(Field("Name", "The Art of Unit Testing", TextField.TYPE_STORED))
        doc2.add(Field("Author", "ROY OSHEROVE", TextField.TYPE_STORED))
        doc2.add(
            Field(
                "Content", """
Don’t be misled as you thumb through the early pages. This is not a mere introduc-
tion to unit testing. It starts that way, and if you’re experienced you can skim those

first chapters. As the book progresses, the chapters start to build on each other into a

rather startling accumulation of depth. Indeed, as I read the last chapter (not knowing it was the last chapter) 
I thought to myself that the next chapter would be dealing

with world peace—because, I mean, where else can you go after solving the problem
of introducing unit testing into obstinate organizations with old legacy systems?
This book is technical—deeply technical. There’s a lot of code. That’s a good
thing. But Roy doesn’t restrict himself to the technical. From time to time he pulls out
his guitar and breaks into song as he tells anecdotes from his professional past or
waxes philosophical about the meaning of design or the definition of integration. He
seems to relish in regaling us with stories about some of the things he did really badly
in the deep, dark past of 2006.
Oh, and don’t be too concerned that the code is all in C#. I mean, who can tell the
difference between C# and Java anyway? Right? And besides, it just doesn’t matter. He
may use C# as a vehicle to communicate his intent, but the lessons in this book also

apply to Java, C, Ruby, Python, PHP, or any other programming language (except, per-
haps COBOL).

If you’re a newcomer to unit testing and test-driven development, or if you’re an
old hand at it, you’ll find this book has something for you. So get ready for a treat as
Roy sings you the song “The Art of Unit Testing.”
And Roy, please tune that guitar!
        """.trimIndent(), TextField.TYPE_NOT_STORED
            )
        )
        doc2.add(IntField("Page", 16, Field.Store.YES))
        index.addDocument(doc2)

        val doc3 = Document()
        doc3.add(Field("Id", "3", TextField.TYPE_STORED))
        doc3.add(Field("Name", "Microservices in Action", TextField.TYPE_STORED))
        doc3.add(Field("Author", "MORGAN BRUCE", TextField.TYPE_STORED))
        doc3.add(
            Field(
                "Content", """
preface
Over the past five years, the microservice architectural style—structuring applications
as fine-grained, loosely coupled, and independently deployable services—has become
increasingly popular and increasingly feasible for engineering teams, regardless of
company size.
For us, working on microservice projects at Onfido was a revelation, and this book
records many of the things we learned along the way. By breaking apart our product,
we could ship faster and with less friction, instead of tripping over each other’s toes in

a large, monolithic codebase. A microservice approach helps engineers build applica-
tions that can evolve over time, even as product complexity and team size grow.

Originally, we set out to write a book about our real-world experience running micro-
service applications. As we scoped the book, that mission evolved, and we decided to

distill our experience of the full application lifecycle—designing, deploying, and oper-
ating microservices—into a broad and practical review. We’ve picked tools to illustrate

these techniques—such as Kubernetes and Docker—that are popular and go hand in
hand with microservice best practice, but we hope that you can apply the lessons within
regardless of which language and tools you ultimately use to build applications.
We sincerely hope you find this book a valuable reference and guide—and that the
knowledge, advice, and examples within help you build great products and applications
with microservices.
        """.trimIndent(), TextField.TYPE_NOT_STORED
            )
        )
        doc3.add(IntField("Page", 10, Field.Store.YES))
        index.addDocument(doc3)

        val doc4 = Document()
        doc4.add(Field("Id", "4", TextField.TYPE_STORED))
        doc4.add(Field("Name", "Microservices in Action", TextField.TYPE_STORED))
        doc4.add(Field("Author", "MORGAN BRUCE", TextField.TYPE_STORED))
        doc4.add(
            Field(
                "Content", """
acknowledgments
In its evolution over the past year and a half, this book has grown from an idea to
write a small book on deploying services to a substantial work covering a wide swath of
microservice development topics—from design to communication to deployment and
operation. It has been our privilege to work with so many talented people in delivering
a book that we truly hope will be useful, both to those who are starting to adopt this
type of architecture and to those who already are using it.
I would like to thank my family, in particular Rosa and Beatriz, my wife and daughter,

who put up with the absences of a husband and father. I would also like to thank Mor-
gan, my coauthor and colleague. He has been crucial in providing guidance and clarity

from day one. Thank you!

—Paulo
This book wouldn’t have been possible to write without the patience and support of
my family, who gracefully tolerated far too many weekends, evenings, and holidays with
me sitting in front of a laptop. I’d also like to thank my parents, Heather and Allan,
who taught me a love of reading, without which I wouldn’t be writing this today. And
lastly, thanks Paulo! You encouraged me to start this project with you, and although the
way was sometimes challenging, I’ve learned so much from the journey.
—Morgan

Together, we would like to thank:
¡ Karen and Dan, our development editors, who were tireless, week after week, in
providing support and advice to help us write the best possible book

¡ Karsten Strøbæk, our technical development editor, for his critical eye and gen-
erous feedback
        """.trimIndent(), TextField.TYPE_NOT_STORED
            )
        )
        doc4.add(IntField("Page", 11, Field.Store.YES))
        index.addDocument(doc4)

        val doc5 = Document()
        doc5.add(Field("Id", "5", TextField.TYPE_STORED))
        doc5.add(Field("Name", "Microservices in Action", TextField.TYPE_STORED))
        doc5.add(Field("Author", "MORGAN BRUCE", TextField.TYPE_STORED))
        doc5.add(
            Field(
                "Content", """
¡ Michael Stephens, for his faith in us, and Marjan Bace, for his help in shaping
our book into something compelling for Manning’s readers

¡ The many other people we’ve worked with at Manning, who formed such a pro-
fessional and talented team, without whom this book would have never been

possible
¡ Lastly, our reviewers, whose feedback and help improving our book we deeply

appreciated, including Akshat Paul, Al Krinker, Andrew Miles, Andy Miles, Anto-
nio Pessolano, Bachir Chihani, Christian Bach, Christian Thoudahl, Vittal Dam-
araju, Deepak Bhaskaran, Evangelos Bardis, John Guthrie, Lorenzo De Leon,

Łukasz Witczak, Maciej Jurkowski, Mike Jensen, Shobha Iyer, Srihari Sridharan,
Steven Parr, Thorsten Weber, and Tiago Boldt Sousa
        """.trimIndent(), TextField.TYPE_NOT_STORED
            )
        )
        doc5.add(IntField("Page", 12, Field.Store.YES))
        index.addDocument(doc5)

    }

    @Test
    fun testSearchOneCorrectWordInOneDocument(){
        val query = "foreword"

        val searchResult = index.search(query, 100)

        assertEquals(1, searchResult.documents.size)
        assertEquals("The Art of Unit Testing", searchResult.documents[0].name)
        assertEquals("ROY OSHEROVE", searchResult.documents[0].author)
        assertEquals(15, searchResult.documents[0].page)
    }

    @Test
    fun testSearchOneCorrectWordInTwoDifferentPages(){
        val query = "people"

        val searchResult = index.search(query, 100)

        assertEquals(2, searchResult.documents.size)
        assertEquals("Microservices in Action", searchResult.documents[0].name)
        assertEquals("Microservices in Action", searchResult.documents[1].name)
        assertEquals("MORGAN BRUCE", searchResult.documents[0].author)
        assertEquals("MORGAN BRUCE", searchResult.documents[1].author)
        assertEquals(11, searchResult.documents[0].page)
        assertEquals(12, searchResult.documents[1].page)
    }

    @Test
    fun testSearchWordWithOneMissedLetter1(){
        val query = "pople"

        val searchResult = index.search(query, 100)

        assertEquals(2, searchResult.documents.size)
        assertEquals("Microservices in Action", searchResult.documents[0].name)
        assertEquals("Microservices in Action", searchResult.documents[1].name)
        assertEquals("MORGAN BRUCE", searchResult.documents[0].author)
        assertEquals("MORGAN BRUCE", searchResult.documents[1].author)
        assertEquals(11, searchResult.documents[0].page)
        assertEquals(12, searchResult.documents[1].page)
    }

    @Test
    fun testSearchWordWithOneMissedLetter2(){
        val query = "unt"

        val searchResult = index.search(query, 100)

        assertEquals(1, searchResult.documents.size)
        assertEquals("The Art of Unit Testing", searchResult.documents[0].name)
        assertEquals("ROY OSHEROVE", searchResult.documents[0].author)
        assertEquals(16, searchResult.documents[0].page)
    }

    @Test
    fun testSearchWordWithTwoMissedLetters(){
        val query = "popl"

        val searchResult = index.search(query, 100)

        assertEquals(2, searchResult.documents.size)
        assertEquals("Microservices in Action", searchResult.documents[0].name)
        assertEquals("Microservices in Action", searchResult.documents[1].name)
        assertEquals("MORGAN BRUCE", searchResult.documents[0].author)
        assertEquals("MORGAN BRUCE", searchResult.documents[1].author)
        assertEquals(11, searchResult.documents[0].page)
        assertEquals(12, searchResult.documents[1].page)
    }

    @Test
    fun testSearchWordWithOneMisspelling(){
        val query = "piople"

        val searchResult = index.search(query, 100)

        assertEquals(2, searchResult.documents.size)
        assertEquals("Microservices in Action", searchResult.documents[0].name)
        assertEquals("Microservices in Action", searchResult.documents[1].name)
        assertEquals("MORGAN BRUCE", searchResult.documents[0].author)
        assertEquals("MORGAN BRUCE", searchResult.documents[1].author)
        assertEquals(11, searchResult.documents[0].page)
        assertEquals(12, searchResult.documents[1].page)
    }

    @Test
    fun testSearchWordWithTwoMisspelling(){
        val query = "pioplt"

        val searchResult = index.search(query, 100)

        assertEquals(2, searchResult.documents.size)
        assertEquals("Microservices in Action", searchResult.documents[0].name)
        assertEquals("Microservices in Action", searchResult.documents[1].name)
        assertEquals("MORGAN BRUCE", searchResult.documents[0].author)
        assertEquals("MORGAN BRUCE", searchResult.documents[1].author)
        assertEquals(11, searchResult.documents[0].page)
        assertEquals(12, searchResult.documents[1].page)
    }

}