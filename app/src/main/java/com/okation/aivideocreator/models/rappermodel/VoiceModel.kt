package com.okation.aivideocreator.models.rappermodel

data class VoiceModel(
    val added_at: Int,
    val architecture: String,
    val category: String,
    val contributors: List<String>,
    val controls: Boolean,
    val display_name: String,
    val hifi_gan_vocoder: String,
    val is_active: Boolean,
    val is_primary: Boolean,
    val is_private: Boolean,
    val language: String,
    val memberships: List<Membership>,
    val ml_model_id: Int,
    val model_id: String,
    val name: String,
    val speaker_id: Int,
    val symbol_set: String,
    val voicemodel_uuid: String
)