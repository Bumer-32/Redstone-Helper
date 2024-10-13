package ua.pp.lumivoid.updater.xmlData

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName


@Serializable
@XmlSerialName("metadata")
data class Metadata(
	@XmlElement val groupId: String,
	@XmlElement val artifactId: String,
	@XmlElement val versioning: Versioning
)
