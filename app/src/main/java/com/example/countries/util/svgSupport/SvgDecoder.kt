package com.example.countries.util.svgSupport

import com.bumptech.glide.load.Options
import com.caverock.androidsvg.SVGParseException
import com.caverock.androidsvg.SVG
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import java.io.IOException
import java.io.InputStream

/**
 * Decodes an SVG internal representation from an {@link InputStream}.
 */
class SvgDecoder : ResourceDecoder<InputStream, SVG> {

    @Throws(IOException::class)
    override fun decode(source: InputStream, width: Int, height: Int, options: Options): Resource<SVG>? {
        try {
            val svg = SVG.getFromInputStream(source)
            return SimpleResource(svg)
        } catch (ex: SVGParseException) {
            throw IOException("Cannot load SVG from stream", ex)
        }
    }

    override fun handles(source: InputStream, options: Options): Boolean = true

}
