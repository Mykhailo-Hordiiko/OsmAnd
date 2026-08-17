package com.hordiiko.osmand.data.local

import android.content.Context
import android.util.Xml
import com.hordiiko.osmand.domain.model.Region
import com.hordiiko.osmand.domain.model.RegionsTree
import org.xmlpull.v1.XmlPullParser

private const val FILE_NAME = "regions.xml"
private const val ENCODING = "UTF-8"

private const val ROOT_CONTINENT_NAME = "europe"
private const val TAG_REGION = "region"

private const val KEY_VALUE_DELIMITER = "="
private const val HYPHEN = "-"
private const val UNDERSCORE = "_"

private const val ATTR_NAME = "name"
private const val ATTR_TYPE = "type"
private const val ATTR_MAP = "map"
private const val ATTR_TRANSLATE = "translate"
private const val ATTR_INNER_DOWNLOAD_PREFIX = "inner_download_prefix"
private const val ATTR_INNER_DOWNLOAD_SUFFIX = "inner_download_suffix"

private const val ID_SEPARATOR = "|"
private const val TRANSLATE_SEPARATOR = ";"
private const val NAME_PREFIX_1 = "name:en="
private const val NAME_PREFIX_2 = "name="
private const val NAME_PREFIX_3 = "="
private const val TYPE_CONTINENT = "continent"
private const val TYPE_SRTM = "srtm"
private const val TYPE_HILLSHADE = "hillshade"
private const val TYPE_MAP = "map"
private const val MAP_VALUE_YES = "yes"
private const val PREFIX_PLACEHOLDER = "\$name"
private const val FILE_NAME_SUFFIX = "_2.obf.zip"

private data class ParsedNode(
    val region: Region,
    val flatSubtree: List<Region>
)

fun parseRegionsTree(context: Context): RegionsTree {
    context.assets
        .open(FILE_NAME)
        .use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setInput(inputStream, ENCODING)
            parser.nextTag()

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType == XmlPullParser.START_TAG
                    && parser.name == TAG_REGION
                ) {
                    val name: String = parser.getAttributeValue(null, ATTR_NAME)

                    if (name == ROOT_CONTINENT_NAME) {
                        val parsedEurope: ParsedNode =
                            parseRegionNode(
                                parser = parser,
                                parentId = null,
                                inheritedPrefix = null,
                                inheritedSuffix = null
                            )

                        return RegionsTree(
                            countries = parsedEurope.region.subRegions,
                            regions = parsedEurope.flatSubtree.associateBy { it.id }
                        )
                    } else {
                        skipCurrentTag(parser)
                    }
                }
            }

            error("Root region '$ROOT_CONTINENT_NAME' not found in $FILE_NAME")
        }
}

private fun parseRegionNode(
    parser: XmlPullParser,
    parentId: String?,
    inheritedPrefix: String?,
    inheritedSuffix: String?
): ParsedNode {
    val name: String = parser.getAttributeValue(null, ATTR_NAME) ?: ""
    val type: String? = parser.getAttributeValue(null, ATTR_TYPE)
    val map: String? = parser.getAttributeValue(null, ATTR_MAP)
    val translate: String? = parser.getAttributeValue(null, ATTR_TRANSLATE)
    val prefix: String? = parser.getAttributeValue(null, ATTR_INNER_DOWNLOAD_PREFIX)
    val suffix: String? = parser.getAttributeValue(null, ATTR_INNER_DOWNLOAD_SUFFIX)

    val id: String = buildId(
        parentId = parentId,
        name = name
    )
    val resolvedName: String = resolveName(
        name = name,
        translate = translate
    )
    val resolvedMap: Boolean = resolveMap(
        map = map,
        type = type
    )
    val resolvedPrefix: String? = resolvePrefix(
        prefix = prefix,
        name = name,
        inheritedPrefix = inheritedPrefix
    )
    val resolvedSuffix: String? = resolveSuffix(
        suffix = suffix,
        inheritedSuffix = inheritedSuffix
    )

    val subRegions = mutableListOf<Region>()
    val parsedChildren = mutableListOf<ParsedNode>()
    val depth: Int = parser.depth

    while (parser.next() != XmlPullParser.END_DOCUMENT) {
        when (parser.eventType) {
            XmlPullParser.START_TAG ->
                if (parser.name == TAG_REGION) {
                    val parsedChild: ParsedNode =
                        parseRegionNode(
                            parser = parser,
                            parentId = id,
                            inheritedPrefix = resolvedPrefix,
                            inheritedSuffix = resolvedSuffix
                        )

                    subRegions.add(parsedChild.region)
                    parsedChildren.add(parsedChild)
                }

            XmlPullParser.END_TAG ->
                if (parser.depth == depth) break
        }
    }

    val fileName: String? =
        resolveFileName(
            resolvedMap = resolvedMap,
            subRegions = subRegions,
            name = name,
            resolvedPrefix = resolvedPrefix,
            resolvedSuffix = resolvedSuffix
        )

    val region = Region(
        id = id,
        name = resolvedName,
        fileName = fileName,
        subRegions = subRegions
    )
    val nestedRegions: List<Region> = parsedChildren.flatMap { it.flatSubtree }

    return ParsedNode(
        region = region,
        flatSubtree = listOf(region) + nestedRegions
    )
}

private fun buildId(parentId: String?, name: String): String =
    if (parentId != null) "$parentId$ID_SEPARATOR$name" else name

private fun resolveName(name: String, translate: String?): String {
    if (translate == null) {
        return formatFallbackName(name)
    }

    val parts: List<String> = translate.split(TRANSLATE_SEPARATOR)

    parts.find { it.startsWith(NAME_PREFIX_1) }
        ?.let { return it.removePrefix(NAME_PREFIX_1) }
    parts.find { it.startsWith(NAME_PREFIX_2) }
        ?.let { return it.removePrefix(NAME_PREFIX_2) }
    parts.find { it.startsWith(NAME_PREFIX_3) }
        ?.let { return it.removePrefix(NAME_PREFIX_3) }

    val firstPart: String = parts.first()

    return if (!firstPart.contains(KEY_VALUE_DELIMITER)) {
        firstPart
    } else {
        formatFallbackName(name)
    }
}

private fun formatFallbackName(name: String): String =
    name.replace(HYPHEN, " ")
        .replace(UNDERSCORE, " ")
        .split(" ")
        .joinToString(" ") {
            it.replaceFirstChar { c ->
                c.uppercase()
            }
        }

private fun resolveMap(map: String?, type: String?): Boolean =
    when (type) {
        TYPE_CONTINENT,
        TYPE_SRTM,
        TYPE_HILLSHADE -> false

        TYPE_MAP -> true
        else -> map?.let {
            it == MAP_VALUE_YES
        } ?: true
    }

private fun resolvePrefix(prefix: String?, name: String, inheritedPrefix: String?): String? =
    when {
        prefix == PREFIX_PLACEHOLDER -> name
        prefix != null -> prefix
        else -> inheritedPrefix
    }

private fun resolveSuffix(suffix: String?, inheritedSuffix: String?): String? =
    suffix ?: inheritedSuffix

private fun resolveFileName(
    resolvedMap: Boolean,
    subRegions: List<Region>,
    name: String,
    resolvedPrefix: String?,
    resolvedSuffix: String?
): String? =
    if (resolvedMap
        && subRegions.isEmpty()
    ) {
        buildFileName(
            name = name,
            resolvedPrefix = resolvedPrefix,
            resolvedSuffix = resolvedSuffix
        )
    } else {
        null
    }

private fun buildFileName(name: String, resolvedPrefix: String?, resolvedSuffix: String?): String {
    val base: String = when {
        resolvedPrefix != null
                && resolvedSuffix != null -> "${resolvedPrefix}_${name}_${resolvedSuffix}"

        resolvedPrefix != null -> "${resolvedPrefix}_${name}"
        resolvedSuffix != null -> "${name}_${resolvedSuffix}"
        else -> name
    }

    return "$base$FILE_NAME_SUFFIX".replaceFirstChar { c ->
        c.uppercase()
    }
}

private fun skipCurrentTag(parser: XmlPullParser) {
    val depth: Int = parser.depth

    while (parser.next() != XmlPullParser.END_DOCUMENT) {
        if (parser.eventType == XmlPullParser.END_TAG
            && parser.depth == depth
        ) break
    }
}