package ua.pp.lumivoid.updater.xmlData

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("versioning")
data class Versioning(
	@XmlElement val latest: String,
	@XmlElement val release: String,
	@XmlElement val versions: Versions,
	@XmlElement val lastUpdated: String
)
