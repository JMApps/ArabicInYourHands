package jmapps.arabicinyourhands.ui.model

data class ContentModel(
    val contentId: Int,
    val ArabicName: String?,
    val ArabicContent: String,
    val TranslationName: String?,
    val TranslationContent: String,
    val NameAudio: String
)