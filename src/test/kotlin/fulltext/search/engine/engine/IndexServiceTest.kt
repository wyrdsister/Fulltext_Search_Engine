package fulltext.search.engine.engine

import fulltext.search.engine.entities.DocInfo
import fulltext.search.engine.entities.Document
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import kotlin.test.BeforeTest

class IndexServiceTest {

    private val indexService = IndexService()

    private val id = "test1"

    @BeforeTest
    fun testCreateIndex(){
        indexService.createIndex(id)

        indexService.addDocument(
            id, Document(
                "1A",
                "Man’s Search for Meaning", "Viktor E. Frankl",
                "Psychiatrist Viktor Frankl's memoir has riveted generations of readers with its descriptions of life in Nazi death camps and its lessons for spiritual survival. Based on his own experience and the stories of his patients, Frankl argues that we cannot avoid suffering but we can choose how to cope with it, find meaning in it, and move forward with renewed purpose. At the heart of his theory, known as logotherapy, is a conviction that the primary human drive is not pleasure but the pursuit of what we find meaningful. Man's Search for Meaning has become one of the most influential books in America; it continues to inspire us all to find significance in the very act of living. The life is so beautiful thing."
                , 0
            )
        )
        indexService.addDocument(
            id, Document(
                "2B",
                "Quiet: The Power of Introverts in a World That Can't Stop Talking", "Susan Cain",
                "At least one-third of the people we know are introverts. They are the ones who prefer listening to speaking; who innovate and create but dislike self-promotion; who favor working on their own over working in teams. It is to introverts—Rosa Parks, Chopin, Dr. Seuss, Steve Wozniak—that we owe many of the great contributions to society. \n" +
                        "\n" +
                        "In Quiet, Susan Cain argues that we dramatically undervalue introverts and shows how much we lose in doing so. She charts the rise of the Extrovert Ideal throughout the twentieth century and explores how deeply it has come to permeate our culture. She also introduces us to successful introverts—from a witty, high-octane public speaker who recharges in solitude after his talks, to a record-breaking salesman who quietly taps into the power of questions. Passionately argued, superbly researched, and filled with indelible stories of real people, Quiet has the power to permanently change how we see introverts and, equally important, how they see themselves.\n" +
                0
            )
        )

        indexService.addDocument(
            id, Document(
                "2D",
                "Quiet: The Power of Introverts in a World That Can't Stop Talking", "Susan Cain",
                "Now with Extra Libris material, including a reader’s guide and bonus content. The life is so awful thing.",
                1
            )
        )
        indexService.addDocument(
            id, Document(
                "3C",
                "The Body Keeps the Score: Brain, Mind, and Body in the Healing of Trauma", "Bessel van der Kolk",
                "A pioneering researcher transforms our understanding of trauma and offers a bold new paradigm for healing.\n" +
                        "\n" +
                        "Trauma is a fact of life. Veterans and their families deal with the painful aftermath of combat; one in five Americans has been molested; one in four grew up with alcoholics; one in three couples have engaged in physical violence. Dr. Bessel van der Kolk, one of the world's foremost experts on trauma, has spent over three decades working with survivors. In The Body Keeps the Score, he uses recent scientific advances to show how trauma literally reshapes both body and brain, compromising sufferers' capacities for pleasure, engagement, self-control, and trust. He explores innovative treatments—from neurofeedback and meditation to sports, drama, and yoga—that offer new paths to recovery by activating the brain's natural neuroplasticity. Based on Dr. van der Kolk's own research and that of other leading specialists, The Body Keeps the Score exposes the tremendous power of our relationships both to hurt and to heal—and offers new hope for reclaiming lives."
                , 0
            )
        )
    }

    @Test
    fun testFindWordInEnglishIndex() {
        val results = indexService.search(id, "life")

        assertEquals(
            listOf(
                DocInfo("1A", "Man’s Search for Meaning", "Viktor E. Frankl", 0),
                DocInfo("2D","Quiet: The Power of Introverts in a World That Can't Stop Talking", "Susan Cain", 1),
                DocInfo( "3C",
                    "The Body Keeps the Score: Brain, Mind, and Body in the Healing of Trauma",
                    "Bessel van der Kolk",
                    0
                )
            ),
            results.documents
        )

    }

    @Test
    fun testFindWholePhraseInEnglishIndex() {
        val results = indexService.search(id, "fact of life")

        assertEquals(
            listOf(
                DocInfo(
                    "3C",
                    "The Body Keeps the Score: Brain, Mind, and Body in the Healing of Trauma",
                    "Bessel van der Kolk",
                    0
                )
            ),
            results.documents
        )

    }

    @Test
    fun testFindPartialPhraseInEnglishIndex() {
        val results = indexService.search(id, "we know are introverts")

        assertEquals(
            listOf(
                DocInfo("2B","Quiet: The Power of Introverts in a World That Can't Stop Talking", "Susan Cain", 0),
            ),
            results.documents
        )
    }

    @Test
    fun testFindPartialPhraseIn2EnglishIndex() {
        val results = indexService.search(id, "Life is thing")

        assertEquals(
            listOf(
                DocInfo("1A", "Man’s Search for Meaning", "Viktor E. Frankl", 0),
                DocInfo("2D", "Quiet: The Power of Introverts in a World That Can't Stop Talking", "Susan Cain", 1),
            ),
            results.documents
        )
    }

    @Test
    fun testFindWordWith1MistakeInEnglishIndex() {
        val results = indexService.search(id, "thig")


        assertEquals(
            listOf(
                DocInfo("1A", "Man’s Search for Meaning", "Viktor E. Frankl", 0),
                DocInfo("2D","Quiet: The Power of Introverts in a World That Can't Stop Talking", "Susan Cain", 1),
            ),
            results.documents
        )

    }


}