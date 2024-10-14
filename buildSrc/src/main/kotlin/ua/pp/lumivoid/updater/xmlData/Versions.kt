package ua.pp.lumivoid.updater.xmlData

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("versions")
data class Versions(
    @XmlElement val version: List<String>
)
