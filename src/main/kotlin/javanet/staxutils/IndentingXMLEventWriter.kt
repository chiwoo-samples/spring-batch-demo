package javanet.staxutils

import java.io.IOException
import java.io.Writer
import javax.xml.namespace.NamespaceContext
import javax.xml.namespace.QName
import javax.xml.stream.*
import javax.xml.stream.events.Characters
import javax.xml.stream.events.EndElement
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

/* $Id: IndentingXMLEventWriter.java,v 1.1 2008/10/06 21:40:20 mdessureault Exp $
 *
 * Copyright (c) 2004, Sun Microsystems, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *      copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 *     * Neither the name of Sun Microsystems, Inc. nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


/**
 * Wraps another [XMLEventWriter] and does the indentation.
 *
 *
 *
 * [XMLEventWriter] API doesn't provide any portable way of
 * doing pretty-printing. This [XMLEventWriter] filter provides
 * a portable indentation support by wrapping another [XMLEventWriter]
 * and adding proper [Characters] event for indentation.
 *
 *
 *
 * Because whitespace handling in XML is tricky, this is not an
 * one-size-fit-all indentation engine. Instead, this class is
 * focused on handling so-called "data-oritented XML" like follows:
 *
 * <pre><xmp>
 * <cards>
 * <card id="kk.152">
 * <firstName>Kohsuke</firstName>
 * <lastName>Kawaguchi</lastName>
</card> *
</cards> *
</xmp></pre> *
 *
 *
 *
 * We'll discuss more about the supported subset of XML later.
 *
 *
 *
 * To use this engine, do as follows:
 * <pre>
 * [XMLEventWriter] w = xmlOutputFactory.createXMLEventWriter(...);
 * w = new [IndentingXMLEventWriter](w);
 *
 * // start writing
</pre> *
 *
 *
 *
 * Use [.setIndent] and [.setNewLine] to
 * control the indentation if you want.
 *
 *
 * <h2>What Subset Does This Support?</h2>
 *
 *
 * This engine works when the content model of each element is either
 * element-only or #PCDATA (but not mixed content model.) IOW, it
 * assumes that the children of any element is either (a) only elements
 * and no #PCDATA or (b) #PCDATA only and no elements.
 *
 *
 *
 * The engine also tries to handle comments, PIs, and a DOCTYPE decl,
 * but in general it works only when those things appear in the
 * element-only content model.
 *
 *
 * <h2>For Maintainers</h2>
 *
 *
 * Please don't try to make this class into an almighty indentation class.
 * I've seen it attempted in Xerces and it's not gonna be pretty.
 *
 *
 *
 * If you come up with an idea of another pretty-printer
 * that supports another subset, please go ahead and write your own class.
 *
 *
 *
 * @author
 * Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
class IndentingXMLEventWriter(core: XMLEventWriter?) : XMLEventWriter {
    private val core: XMLEventWriter

    /**
     * String used for indentation.
     */
    private var indent = "  "

    /**
     * String for EOL.
     */
    private var newLine: String? = null

    /**
     * Current nest level.
     */
    private var depth = 0

    /**
     * True if the current element has text.
     */
    private var hasText = false

    /**
     * [XMLEvent] constant that returns the [.newLine].
     */
    private val newLineEvent: Characters = object : CharactersImpl() {
        override fun getData(): String {
            return newLine!!
        }
    }

    /**
     * [XMLEvent] constant that returns the [.indent].
     */
    private val indentEvent: Characters = object : CharactersImpl() {
        override fun getData(): String {
            return indent
        }
    }

    /**
     * Partial implementation of [Characters] event.
     */
    private abstract class CharactersImpl : Characters {
        override fun isWhiteSpace(): Boolean {
            return true
        }

        override fun isCData(): Boolean {
            return false
        }

        override fun isIgnorableWhiteSpace(): Boolean {
            // this is hard call. On one hand, we want the indentation to
            // get through whatever pipeline, so we are tempted to return false.
            // also DTD isn't necessarily present.
            //
            // But on the other hand, this IS an ignorable whitespace
            // in its intended meaning.
            return true
        }

        override fun getEventType(): Int {
            // it's not clear if we are supposed to return SPACES
            return XMLStreamConstants.CHARACTERS
        }

        override fun getLocation(): Location? {
            // spec isn't clear if we can return null, but it doesn't say we can't.
            return null
        }

        override fun isStartElement(): Boolean {
            return false
        }

        override fun isAttribute(): Boolean {
            return false
        }

        override fun isNamespace(): Boolean {
            return false
        }

        override fun isEndElement(): Boolean {
            return false
        }

        override fun isEntityReference(): Boolean {
            return false
        }

        override fun isProcessingInstruction(): Boolean {
            return false
        }

        override fun isCharacters(): Boolean {
            return true
        }

        override fun isStartDocument(): Boolean {
            return false
        }

        override fun isEndDocument(): Boolean {
            return false
        }

        override fun asStartElement(): StartElement? {
            return null
        }

        override fun asEndElement(): EndElement? {
            return null
        }

        override fun asCharacters(): Characters {
            return this
        }

        override fun getSchemaType(): QName? {
            return null
        }

        @Throws(XMLStreamException::class)
        override fun writeAsEncodedUnicode(writer: Writer) {
            try {
                // technically we need to do escaping, for we allow
                // any characters to be used for indent and newLine.
                // but in practice, who'll use something other than 0x20,0x0D,0x0A,0x08?
                writer.write(data)
            } catch (e: IOException) {
                throw XMLStreamException(e)
            }
        }
    }

    init {
        requireNotNull(core)
        this.core = core

        // get the default line separator
        newLine = try {
            System.getProperty("line.separator")
        } catch (e: SecurityException) {
            // use '\n' if we can't figure it out
            "\n"
        }
    }

    /**
     * Returns the string used for indentation.
     */
    fun getIndent(): String {
        return indent
    }

    /**
     * Sets the string used for indentation.
     *
     *
     *
     * By default, this is set to two space chars.
     *
     * @param indent
     * A string like "  ", "\\t". Must not be null.
     */
    fun setIndent(indent: String?) {
        requireNotNull(indent)
        this.indent = indent
    }

    /**
     * Returns the string used for newline.
     */
    fun getNewLine(): String? {
        return newLine
    }

    /**
     * Sets the string used for newline.
     *
     *
     *
     * By default, this is set to the platform default new line.
     *
     * @param newLine
     * A string like "\\n" or "\\r\\n". Must not be null.
     */
    fun setNewLine(newLine: String?) {
        requireNotNull(newLine)
        this.newLine = newLine
    }

    @Throws(XMLStreamException::class)
    override fun add(event: XMLEvent) {
        when (event.eventType) {
            XMLStreamConstants.CHARACTERS, XMLStreamConstants.CDATA, XMLStreamConstants.SPACE -> {
                if (event.asCharacters().isWhiteSpace) // skip any indentation given by the client
                // we are running the risk of ignoring the non-ignorable
                // significant whitespaces, but that's a risk explained
                // in the class javadoc.
                    return
                hasText = true
                core.add(event)
                return
            }
            XMLStreamConstants.START_ELEMENT -> {
                newLine()
                core.add(event)
                hasText = false
                depth++
                return
            }
            XMLStreamConstants.END_ELEMENT -> {
                depth--
                if (!hasText) {
                    newLine()
                }
                core.add(event)
                hasText = false
                return
            }
            XMLStreamConstants.PROCESSING_INSTRUCTION, XMLStreamConstants.COMMENT, XMLStreamConstants.DTD -> {
                // those things can be mixed with text,
                // and at this point we don't know if text follows this
                // like <foo><?pi?>text</foo>
                //
                // but we make a bold assumption that the those primitives
                // only appear as a part of the element-only content model.
                // so we always indent them as:
                // <foo>
                //   <?pi?>
                //   ...
                // </foo>
                if (!hasText) {
                    // if we know that we already had a text, I see no
                    // reason to indent
                    newLine()
                }
                core.add(event)
                return
            }
            XMLStreamConstants.END_DOCUMENT -> {
                core.add(event)
                // some implementation does the buffering by default,
                // and it prevents the output from appearing.
                // this has been a confusion for many people.
                // calling flush wouldn't hurt decent impls, and it
                // prevent such unnecessary confusion.
                flush()
            }
            else -> {
                core.add(event)
                return
            }
        }
    }

    /**
     * Prints out a new line and indent.
     */
    @Throws(XMLStreamException::class)
    private fun newLine() {
        core.add(newLineEvent)
        for (i in 0 until depth) core.add(indentEvent)
    }

    @Throws(XMLStreamException::class)
    override fun add(reader: XMLEventReader) {
        // we can't just delegate to the core
        // because we need to do indentation.
        requireNotNull(reader)
        while (reader.hasNext()) {
            add(reader.nextEvent())
        }
    }

    @Throws(XMLStreamException::class)
    override fun close() {
        core.close()
    }

    @Throws(XMLStreamException::class)
    override fun flush() {
        core.flush()
    }

    override fun getNamespaceContext(): NamespaceContext {
        return core.namespaceContext
    }

    @Throws(XMLStreamException::class)
    override fun getPrefix(uri: String): String {
        return core.getPrefix(uri)
    }

    @Throws(XMLStreamException::class)
    override fun setDefaultNamespace(uri: String) {
        core.setDefaultNamespace(uri)
    }

    @Throws(XMLStreamException::class)
    override fun setNamespaceContext(context: NamespaceContext) {
        core.namespaceContext = context
    }

    @Throws(XMLStreamException::class)
    override fun setPrefix(prefix: String, uri: String) {
        core.setPrefix(prefix, uri)
    }
}