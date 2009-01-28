// Copyright 2009 Information Without Borders
// 
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

#include <libxml/encoding.h>
#include <libxml/xmlwriter.h>

#include <stdio.h>
#include <string.h>

#define MY_ENCODING "ISO-8859-1"

void printInterestAsXML(char *attr_name, char *attr_value, long attr_weight)
{
    int rc;
    xmlTextWriterPtr writer;
    xmlBufferPtr buf;

    /* Create a new XML buffer, to which the XML document will be
     * written */
    buf = xmlBufferCreate();
    if (buf == NULL) {
        printf("testXmlwriterMemory: Error creating the xml buffer\n");
        return;
    }

    /* Create a new XmlWriter for memory, with no compression.
     * Remark: there is no compression for this kind of xmlTextWriter */
    writer = xmlNewTextWriterMemory(buf, 0);
    if (writer == NULL) {
        printf("testXmlwriterMemory: Error creating the xml writer\n");
        return;
    }

    /* Start the document with the xml default for the version,
     * encoding ISO 8859-1 and the default for the standalone
     * declaration. */
    rc = xmlTextWriterStartDocument(writer, NULL, MY_ENCODING, NULL);
    if (rc < 0) {
        printf
            ("testXmlwriterMemory: Error at xmlTextWriterStartDocument\n");
        return;
    }

    rc = xmlTextWriterStartElement(writer, BAD_CAST "interest");
    if (rc < 0) {
        printf
            ("testXmlwriterMemory: Error at xmlTextWriterStartElement\n");
        return;
    }

	rc = xmlTextWriterWriteAttribute(writer, BAD_CAST "name", BAD_CAST attr_name);
    if (rc < 0) {
        printf
            ("testXmlwriterFilename: Error at xmlTextWriterWriteAttribute\n");
        return;
    }

	rc = xmlTextWriterWriteAttribute(writer, BAD_CAST "value", BAD_CAST attr_value);
    if (rc < 0) {
        printf
            ("testXmlwriterFilename: Error at xmlTextWriterWriteAttribute\n");
        return;
    }

	// Why does this blow up?
	// rc = xmlTextWriterWriteAttribute(writer, BAD_CAST "weight", BAD_CAST attr_weight);
	//     if (rc < 0) {
	//         printf
	//             ("testXmlwriterFilename: Error at xmlTextWriterWriteAttribute\n");
	//         return;
	//     }

    /* Here we could close the elements ORDER and EXAMPLE using the
     * function xmlTextWriterEndElement, but since we do not want to
     * write any other elements, we simply call xmlTextWriterEndDocument,
     * which will do all the work. */
    rc = xmlTextWriterEndDocument(writer);
    if (rc < 0) {
        printf("testXmlwriterMemory: Error at xmlTextWriterEndDocument\n");
        return;
    }

    xmlFreeTextWriter(writer);

    printf("%s\n", (const char *) buf->content);

    xmlBufferFree(buf);
}
