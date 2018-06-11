package org.prepciudadano.prepciudadano.firebase

class Template(
        val templateId: String,
        val boxId: String,
        val image: String,
        val status:Int,
        val likes:Int,
        val unlikes:Int,
        val morena:Int,
        val pan: Int,
        val pri: Int,
        val prd: Int,
        val mc: Int,
        val pvem: Int,
        val pt: Int,
        val panal: Int,
        val pes: Int
    ){

    constructor():this("", "", "", 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0)
}